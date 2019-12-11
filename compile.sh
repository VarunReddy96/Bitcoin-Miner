#!/bin/bash

cd CSCI654

git clone https://gitlab.com/moovlin/group-project-1.git

cd group-project-1

javac -d classes/ edu/rit/cs/CoinMining/NetworkParallelMiner.java edu/rit/cs/CoinMining/ClientNetworkListner.java edu/rit/cs/CoinMining/clientpacketanalyzer.java edu/rit/cs/CoinMining/ClientServerWriter.java edu/rit/cs/CoinMining/MinerCallable.java edu/rit/cs/CoinMining/MinerListenerInterface.java edu/rit/cs/CoinMining/MinerNotifierInterface.java edu/rit/cs/CoinMining/packetanalyzer.java edu/rit/cs/CoinMining/ThreadPoolManager.java edu/rit/cs/CoinMining/MinerNotifier.java  edu/rit/cs/CoinMining/MasterWriter.java edu/rit/cs/CoinMining/MasterListner.java edu/rit/cs/CoinMining/ClientNetworkListner.java -Xlint:unchecked edu/rit/cs/CoinMining/MasterNode.java edu/rit/cs/CoinMining/MasterManager.java edu/rit/cs/CoinMining/MinerThreadPoolExecutor.java edu/rit/cs/CoinMining/MinerCallable.java

# run node
# java -cp classes/ edu.rit.cs.CoinMining.NetworkParallelMiner 127.0.0.1 5400 2400

# run master
# java -cp classes/ edu.rit.cs.CoinMining.MasterNode 5400 "0aca36d7d8e3bd46e6bab5bf3a47230e91e100ccd241c169e9d375f5b2a28f82" "0000092a6893b712892a41e8438e3ff2242a68747105de0395826f60b38d88dc"
