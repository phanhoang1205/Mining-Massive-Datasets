# Apache Spark and Hadoop MapReduce

## Set up Hadoop

Operation system: Ubuntu 18.04 LTS

---
### Install Hadoop

1. install Java 8
    ```bash
    sudo apt install openjdk-8-jre-headless
    sudo apt install openjdk-8-jdk-headless
    ``` 

2. Install ssh and pdsh
    ```bash
    sudo apt install ssh
    sudo apt install pdsh
    ```

3. Setup passphrase for ssh
    ```bash
    ssh-keygen -t rsa -P '' -f ~/.ssh/id_rsa
    cat ~/.ssh/id_rsa.pub >> ~/.ssh/authorized_keys
    chmod 0600 ~/.ssh/authorized_keys
    ```
    Note: ensure the file ~/.ssh/authorized_keys exists \
    Check whether you can ssh to localhost:
    ``` 
    ssh localhost
    ```


4. Configure rcmd to ssh as default
    ```bash
    sudo echo “ssh” > /etc/pdsh/rcmd_default
    ```

5. Download Hadoop 3.2.1 
    ```bash
    wget https://archive.apache.org/dist/hadoop/common/hadoop-3.2.1/hadoop-3.2.1.tar.gz
	tar -xvf hadoop-3.2.1.tar.gz
    ```

6. Declare JAVA_HOME for Hadoop
    ```bash
    nano etc/hadoop/hadoop-env.sh
    ```

    Add the following line below to the end of the file and check your own Java path if different 
    ```bash
    export JAVA_HOME=/usr/lib/jvm/java-1.8.0-openjdk-amd64
    export PATH=${JAVA_HOME}/bin:${PATH}
    export HADOOP_CLASSPATH=${JAVA_HOME}/lib/tools.jar
    ```

---
### Set up Pseudo-Distributed Mode

Inside the hadoop folder, Edit these following files:

**etc/hadoop/core-site.xml**
```bash
<configuration>
    <property>
        <name>fs.defaultFS</name>
        <value>hdfs://localhost:9000</value>
    </property>
</configuration>
```

**etc/hadoop/hdfs-site.xml**
```bash
<configuration>
    <property>
        <name>dfs.replication</name>
        <value>1</value>
    </property>
</configuration>
```

---
## Run a MapReduce job locally
1. Format the filesystem
    ```bash 
    bin/hdfs namenode -format
    ```

2. Start NameNode daemon and DataNode daemon
    ```bash
    sbin/start-dfs.sh
    ```

    Note: **if you fail to start a daemon then** use code below and try to start again
    ```bash
    sbin/stop-all.sh
    sudo rm -rf /tmp/*
    sudo reboot
    bin/hdfs namenode -format -force
    ```

3. Browse the web interface for the NameNode; by default it is available at 

    http://localhost:9870/

4. Make the HDFS directories required to execute MapReduce jobs:
    ```bash
    bin/hdfs dfs -mkdir /user
    bin/hdfs dfs -mkdir /user/<username>
    ```

5. Copy the input files into the distributed file system:
    ```bash
    bin/hdfs dfs -mkdir input
    bin/hdfs dfs -put etc/hadoop/*.xml /user/<username>/input
    ```

6. Run some of the examples provided (need to set up YARN in advance)
    ```bash
    bin/hadoop jar share/hadoop/mapreduce/hadoop-mapreduce-examples-3.2.1.jar grep input output 'dfs[a-z.]+'
    ```

    ensure the corresponding jar file exists in the folder mapreduce/

7. Examine the output files: Copy the output files from the distributed file system to the local filesystem and examine them:
    ```bash
	bin/hdfs dfs -get output output
    cat output/*
    ```

    Or view the output files on the distributed file system:
    ```bash
    bin/hdfs dfs -cat output/*
    ```

8. When you’re done, stop the daemons with:
    ```bash
	sbin/stop-dfs.sh
    ```

---
## Execute job on YARN
The following instructions assume that 1. ~ 4. steps of the above instructions are already executed.

Inside the hadoop folder, configure parameters as follows:

**etc/hadoop/mapred-site.xml**
```bash
<configuration>
    <property>
        <name>mapreduce.framework.name</name>
        <value>yarn</value>
    </property>
    <property>
        <name>mapreduce.application.classpath</name>
        <value>$HADOOP_MAPRED_HOME/share/hadoop/mapreduce/*:$HADOOP_MAPRED_HOME/share/hadoop/mapreduce/lib/*</value>
    </property>
</configuration>
```

**etc/hadoop/yarn-site.xml**
```bash
<configuration>
    <property>
        <name>yarn.nodemanager.aux-services</name>
        <value>mapreduce_shuffle</value>
    </property>
    <property>
        <name>yarn.nodemanager.env-whitelist</name>
        <value>JAVA_HOME,HADOOP_COMMON_HOME,HADOOP_HDFS_HOME,HADOOP_CONF_DIR,CLASSPATH_PREPEND_DISTCACHE,HADOOP_YARN_HOME,HADOOP_MAPRED_HOME</value>
    </property>
</configuration>
```

6. Start ResourceManager daemon and NodeManager daemon:
    ```bash
    sbin/start-yarn.sh
    ```

7. Browse the web interface for the ResourceManager; by default it is available at: http://localhost:8088/

8. Run a MapReduce job.
9. When you’re done, stop the daemons with:
    ```bash
    sbin/stop-yarn.sh
    ```
---
## Counting Coronavirus case in ASEAN

Given a tsv file WHO-COVID-19-20210601-213841.tsv which is corresponding to the WHO Coronavirus (COVID-19) Dashboard.

---
MapReduce is a style of programming designed for
1. Easy parallel programming
2. Invisible management of hardware and software failures
3. Easy management of very-large-scale data

It has several implementations, including Hadoop, Spark (used in this class), Flink, and the original Google implementation just called “MapReduce"

3 steps of MapReduce

**Map**: Apply a user-written Map function to each input element


**Group by key**: Sort and shuffle. System sorts all the key-value pairs by key, and outputs key-(list of values) pairs

**Reduce**: User-written Reduce functionis applied to each key-(list of values)

Move the data from local machine to hdfs
```bash
bin/hdfs dfs -put WHO-COVID-19-20210601-213841.tsv input
bin/hdfs dfs -ls input
```
    

* Count Coronavirus case in ASEAN using PySpark
    ```bash
    $SPARK_HOME/bin/spark-submit ASEANCaseCount.py
    ```

* Compile java file to jar
    ```bash
    bin/hadoop com.sun.tools.javac.Main ASEANCaseCount.java 

    jar cf acc.jar ASEANCaseCount*.class
    ```
    Count Coronavirus case in ASEAN using MapReduce
    ```bash
    bin/hadoop jar acc.jar ASEANCaseCount input output
    ```