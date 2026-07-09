# Spring boot & kafka projects

Tectnology: Java 17, Spring boot 3, apache kafka

<h3>1. Demo app to explore apache kafka & Key terminology. : spring-boot-kafka-app </h3>
<h3>2. Real world App : springboot-kafka-real-world-project</h3>
<h3>3. Event Drivern Microservers using kafka </h3>

<br>

Q.1 What is Apache Kafka & what are main terminology for the same?


	1. Producer & Producer Group
	2. Consumer & Producer Group
	3. Kafka Cluster 
	4. Zookeeper
	5. Kafka Brokers
	6. Topic
	7. Partition
	8. Offset

Applications:

Q.2 How to setup Kakfa to windows system?

STEP 1: GET KAFKA

Download & setup on windows: https://kafka.apache.org/quickstart


	$ tar -xzf kafka_2.13-3.7.0.tgz
$ cd kafka_2.13-3.7.0


*** Rename folder to kafka in windows 

STEP 2: START THE KAFKA ENVIRONMENT

Kafka with ZooKeeper

	a. Run the following commands in order to start all services in the correct order:

		# Start the ZooKeeper service -- WINDOWS

		$ .\bin\windows\zookeeper-server-start.bat .\config\zookeeper.properties
	
		D:\Study\env\config\kafka>.\bin\windows\zookeeper-server-start.bat .\config\zookeeper.properties

	b. Open another terminal session and run:

		Start the Kafka broker service
	
		$ .\bin\windows\kafka-server-start.bat .\config\server.properties
	
		i.e D:\Study\env\config\kafka>.\bin\windows\kafka-server-start.bat .\config\server.properties


STEP 3: CREATE A TOPIC TO STORE YOUR EVENTS

	$ .\bin\windows\kafka-topics.bat --create --topic quickstart-events --bootstrap-server localhost:9092

	D:\Study\env\config\kafka>.\bin\windows\kafka-topics.bat --create --topic quickstart-events --bootstrap-server localhost:9092
	Created topic quickstart-events.


STEP 4: WRITE SOME EVENTS INTO THE TOPIC


	$ .\bin\windows\kafka-console-producer.bat --topic quickstart-events --bootstrap-server localhost:9092
	
	D:\Study\env\config\kafka>.\bin\windows\kafka-console-producer.bat --topic quickstart-events --bootstrap-server localhost:9092
	>Hello Jarvis
	>Catmanscode demo
	>hello cat


STEP 5: READ THE EVENTS

	$ .\bin\windows\kafka-console-consumer.bat --topic quickstart-events --from-beginning --bootstrap-server localhost:9092
	
	D:\Study\env\config\kafka>.\bin\windows\kafka-console-consumer.bat --topic quickstart-events --from-beginning --bootstrap-server localhost:9092
	
	Hello Jarvis
	Catmanscode demo

---

# Apache Kafka (v3.7.0) KRaft Mode Setup on Windows

This guide provides a step-by-step walkthrough to set up and run Apache Kafka version `3.7.0` in **KRaft (Kafka Raft) mode** on a local Windows machine. 

With KRaft mode, **ZooKeeper is not required**. Kafka manages its own metadata cluster internally.

---

## 📋 Prerequisites

Before starting, ensure you have the following installed on your Windows machine:
* **Java JDK 11 or higher** (Ensure `JAVA_HOME` is set in your environment variables).
* **Kafka Binary Archive**: `kafka_2.13-3.7.0.tgz` extracted to your local drive (e.g., `D:\Study\env\config\kafka`).

---

## 🚀 Step-by-Step Installation & Execution

Open your **Command Prompt (cmd)** as an Administrator and navigate to your Kafka root installation directory:

```cmd
cd D:\Study\env\config\kafka
```

### Step 1: Generate a Cluster UUID
Kafka KRaft requires a unique ID to identify the cluster. Generate a random UUID by running:

```cmd
.\bin\windows\kafka-storage.bat random-uuid
```
* **Action Required:** Copy the generated unique string output (e.g., `xt99ep6YTW669...`) from the console.

### Step 2: Format the Kafka Storage Directory
Format your log directories using the cluster UUID you copied in the previous step. Replace `YOUR_GENERATED_UUID` with your actual string:

```cmd
.\bin\windows\kafka-storage.bat format -t YOUR_GENERATED_UUID -c .\config\kraft\server.properties
```
* You should see a success message: `Formatting metadata directory...`

### Step 3: Start the Kafka Server
Launch the single-node Kafka broker using the KRaft configuration file. **Keep this command prompt window open** to keep Kafka running:

```cmd
.\bin\windows\kafka-server-start.bat .\config\kraft\server.properties
```

---

## 🧪 Verifying the Setup (PoC Verification)

To verify that your ZooKeeper-less cluster is running perfectly, open a **new, separate Command Prompt window**, navigate to your Kafka directory, and run the following verification tests:

### 1. Create a Test Topic
Create a new topic named `test-topic`:

```cmd
.\bin\windows\kafka-topics.bat --create --topic test-topic --bootstrap-server localhost:9092
```

### 2. Start a Console Producer (Send Messages)
Run the producer to open an interactive terminal session where you can type messages:

```cmd
.\bin\windows\kafka-console-producer.bat --topic test-topic --bootstrap-server localhost:9092
```
* *Type a few lines of text (e.g., "Hello Kafka", "Testing KRaft Mode") and press Enter after each.*

### 3. Start a Console Consumer (Receive Messages)
Open a **third** Command Prompt window and run the consumer to read the streamed data from the beginning:

```cmd
.\bin\windows\kafka-console-consumer.bat --topic test-topic --from-beginning --bootstrap-server localhost:9092
```
* *You should instantly see the messages you typed in the producer terminal appear here.*

---

## 🛠️ Troubleshooting Windows-Specific Issues

### "The input line is too long" Error
* **Cause:** Windows command line limits script paths if your Kafka installation directory path is too deep.
* **Fix:** Move the extracted Kafka directory closer to the drive root (e.g., `D:\kafka`) and rerun the steps.

### Port Conflicts
* KRaft mode uses port `9092` for client traffic and port `9093` for controller quorum communication. Ensure these ports are not being used by other local applications.



