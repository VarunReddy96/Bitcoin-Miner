For the code to perform Bitcoin mining operations

Reserved ports: 6400

Compile all the files in the Master as well as worker nodes using:

javac *.java

Step-1

To start the Master node

java MasterNode portnumber blockhash targethash

portnumber: the port on which the Master Listens.
blockhash: The inputblockhash
targethash: The targethash

Step-2
Now start the client nodes

To start the client nodes

java NetworkParallelMiner ipaddress portnumber

ipaddress: The ipaddress of the Master node
portnumber: The port number on which the master is listening on

Step-3

Now go to the Master terminal and enter 's' and click enter to start the Mining operations.

