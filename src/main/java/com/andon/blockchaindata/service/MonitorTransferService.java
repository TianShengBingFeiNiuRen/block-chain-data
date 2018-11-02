package com.andon.blockchaindata.service;

import com.andon.blockchaindata.domain.BlockMe;
import com.andon.blockchaindata.domain.MonitorTransferRequest;
import com.andon.blockchaindata.domain.TransactionMe;
import com.andon.blockchaindata.domain.TransferRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.EthBlockNumber;
import org.web3j.protocol.http.HttpService;
import org.web3j.protocol.ipc.UnixIpcService;
import rx.Subscription;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

@Service
public class MonitorTransferService {

    private static final Logger LOG = LoggerFactory.getLogger(MonitorTransferService.class);
    private Web3j web3j;
    private ThreadLocal<Set<BigInteger>> blockNumberSet = ThreadLocal.withInitial(HashSet::new);

    @Value("${ipcSocketPath}")
    private String ipcSocketPath;

    @PostConstruct
    public void init() {
//        web3j = Web3j.build(new HttpService()); //RPC方式
        web3j = Web3j.build(new UnixIpcService(ipcSocketPath)); //IPC方式
    }

    /**
     * 监听合约(分步)
     */
    public void contractFilterOfStep(MonitorTransferRequest monitorTransferRequest) {
        new Thread(() -> {
            try {
                BigInteger blockNumber = getBlockNumber();
                LOG.info("BlockChainNumber={}", blockNumber);
                BigInteger startBlock = new BigInteger(monitorTransferRequest.getFirstBlock());
                BigInteger step = new BigInteger("100");
                assert blockNumber != null;
                int num = Integer.parseInt(String.valueOf(blockNumber.subtract(startBlock).divide(step)));
                for (int i = 0; i < num; i++) {
                    // 监听合约(分步)
                    contractFilterStep(monitorTransferRequest, startBlock, startBlock.add(step));
                    startBlock = startBlock.add(step);
                }
                // 监听合约
                contractFilter(monitorTransferRequest, startBlock);
            } catch (NumberFormatException e) {
                LOG.error("监控合约异常终止,e={}", e.getMessage());
            }
        }).start();
    }

    /**
     * 监听合约
     */
    private void contractFilter(MonitorTransferRequest monitorTransferRequest, BigInteger startBlock) {
        Event event = new Event("Transfer",
                Arrays.asList(
                        new TypeReference<Address>() {
                        },
                        new TypeReference<Address>() {
                        }),
                Collections.singletonList(new TypeReference<Uint256>() {
                }));
        EthFilter filter = new EthFilter(
                DefaultBlockParameter.valueOf(startBlock),
                DefaultBlockParameterName.LATEST,
                monitorTransferRequest.getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(event));
        // 监听并处理
        contractExtract(filter);
    }

    /**
     * 监听并处理
     */
    private void contractExtract(EthFilter filter) {
        Subscription subscription = web3j.ethLogObservable(filter).subscribe(log -> {
            BigInteger blockNumber = log.getBlockNumber();
            // 提取转账记录
            LOG.info("BlockNumber=", blockNumber);
            blockNumberSet.get().add(blockNumber);
            List<String> topics = log.getTopics();
            String fromAddress = topics.get(1);
            String toAddress = topics.get(2);
            String value = log.getData();
            String timestamp = "";

            try {
                EthBlock ethBlock = web3j.ethGetBlockByNumber(DefaultBlockParameter.valueOf(log.getBlockNumber()), false).send();
                timestamp = String.valueOf(ethBlock.getBlock().getTimestamp());
            } catch (IOException e) {
                LOG.warn("Block timestamp get failure,block number is {}", log.getBlockNumber());
                LOG.error("Block timestamp get failure,{}", e);
            }

            TransferRecord transferRecord = new TransferRecord();
            transferRecord.setFromAddress("0x" + fromAddress.substring(26));
            transferRecord.setToAddress("0x" + toAddress.substring(26));
            transferRecord.setValue(new BigDecimal(new BigInteger(value.substring(2), 16)).divide(BigDecimal.valueOf(1000000000000000000.0), 18, BigDecimal.ROUND_HALF_EVEN));
            transferRecord.setTimeStamp(timestamp);
        });
//        subscription.unsubscribe();
    }

    /**
     * 监听合约(分步)
     */
    private void contractFilterStep(MonitorTransferRequest monitorTransferRequest, BigInteger startBlock, BigInteger endBlock) {
        Event event = new Event("Transfer",
                Arrays.asList(
                        new TypeReference<Address>() {
                        },
                        new TypeReference<Address>() {
                        }),
                Collections.singletonList(new TypeReference<Uint256>() {
                }));
        EthFilter filter = new EthFilter(
                DefaultBlockParameter.valueOf(startBlock),
                DefaultBlockParameter.valueOf(endBlock),
                monitorTransferRequest.getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(event));
        // 监听并处理
        contractExtract(filter);
    }

    /**
     * 区块数量
     */
    private BigInteger getBlockNumber() {
        EthBlockNumber send;
        try {
            send = web3j.ethBlockNumber().send();
            return send.getBlockNumber();
        } catch (IOException e) {
            LOG.warn("请求区块链信息异常 >> 区块数量,{}", e);
        }
        return null;
    }

    /**
     * 遍历旧区块
     *
     * @param startBlock 起始区块高度
     * @param endBlock   截止区块高度
     */
    public void replayBlocksObservable(BigInteger startBlock, BigInteger endBlock) {
        web3j.replayBlocksObservable(
                DefaultBlockParameter.valueOf(startBlock),
                DefaultBlockParameter.valueOf(endBlock),
                false).
                subscribe(ethBlock -> {
                    EthBlock.Block block = ethBlock.getBlock();
                    System.out.println("replay block, BlockNumber=" + block.getNumber());
                    System.out.println(BlockMe.toStr(block));
                });
    }

    /**
     * 遍历旧交易
     *
     * @param startBlock 起始区块高度
     * @param endBlock   截止区块高度
     */
    public void replayTransactionsObservable(BigInteger startBlock, BigInteger endBlock) {
        web3j.replayTransactionsObservable(
                DefaultBlockParameter.valueOf(startBlock),
                DefaultBlockParameter.valueOf(endBlock)).
                subscribe(transaction -> {
                    System.out.println("replay transaction, BlockNumber=" + transaction.getBlockNumber());
                    System.out.println(TransactionMe.toStr(transaction));
                });
    }

    /**
     * 遍历旧区块,监听新区快
     *
     * @param startBlock 起始区块高度
     */
    public void catchUpToLatestAndSubscribeToNewBlocksObservable(BigInteger startBlock) {
        web3j.catchUpToLatestAndSubscribeToNewBlocksObservable(
                DefaultBlockParameter.valueOf(startBlock), false)
                .subscribe(ethBlock -> {
                    EthBlock.Block block = ethBlock.getBlock();
                    System.out.println("block," + block.getNumber());
                    System.out.println(BlockMe.toStr(block));
                });
    }

    /**
     * 遍历旧交易,监听新交易
     *
     * @param startBlock 起始区块高度
     */
    public void catchUpToLatestAndSubscribeToNewTransactionsObservable(BigInteger startBlock) {
        web3j.catchUpToLatestAndSubscribeToNewTransactionsObservable(
                DefaultBlockParameter.valueOf(startBlock))
                .subscribe(transaction -> {
                    System.out.println("transaction, BlockNumber=" + transaction.getBlockNumber());
                    System.out.println(TransactionMe.toStr(transaction));
                });
    }
}
