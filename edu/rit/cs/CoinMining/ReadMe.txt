For the code to perform Bitcoin mining operations

Reserved ports: 6400

Compile all the files in the Master as well as worker nodes using:

javac -d classes/ edu/rit/cs/CoinMining/NetworkParallelMiner.java edu/rit/cs/CoinMining/ClientNetworkListner.java edu/rit/cs/CoinMining/clientpacketanalyzer.java edu/rit/cs/CoinMining/ClientServerWriter.java edu/rit/cs/CoinMining/MinerCallable.java edu/rit/cs/CoinMining/MinerListenerInterface.java edu/rit/cs/CoinMining/MinerNotifierInterface.java edu/rit/cs/CoinMining/packetanalyzer.java edu/rit/cs/CoinMining/ThreadPoolManager.java edu/rit/cs/CoinMining/MinerNotifier.java  edu/rit/cs/CoinMining/MasterWriter.java edu/rit/cs/CoinMining/MasterListner.java edu/rit/cs/CoinMining/ClientNetworkListner.java -Xlint:unchecked edu/rit/cs/CoinMining/MasterNode.java edu/rit/cs/CoinMining/MasterManager.java edu/rit/cs/CoinMining/MinerThreadPoolExecutor.java edu/rit/cs/CoinMining/MinerCallable.java

Step-1

To start the Master node
java -cp classes/ edu.rit.cs.CoinMining.MasterNode 5400 <blockhash> <targethash>

portnumber: the port on which the Master Listens.
blockhash: The inputblockhash
targethash: The targethash

Step-2
Now start the client nodes

To start the client nodes
java -cp classes/ edu.rit.cs.CoinMining.NetworkParallelMiner <ipaddr/hostname>
<port> <extra>

ipaddress: The ipaddress of the Master node
portnumber: The port number on which the master is listening on

Step-3

Now go to the Master terminal and enter 's' and click enter to start the Mining operations.

