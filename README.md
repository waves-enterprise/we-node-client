# we-node-client

Java/Kotlin SDK Node client and domain.

we-node-client allows you to create and use client nodes with flexible settings through wrappers of [NodeBlockingServiceFactory](we-node-client-blocking-client%2Fsrc%2Fmain%2Fkotlin%2Fcom%2Fwavesenterprise%2Fsdk%2Fnode%2Fclient%2Fblocking%2Fnode%2FNodeBlockingServiceFactory.kt).

Our primary goals are:
1. Provide flexibility to use the client in the client code by making abstractions for the possibility of overriding.
2. Providing basic functionality for the correct and stable operation of the clients. For example:
   - [RateLimitingServiceFactory](we-node-client-blocking-client%2Fsrc%2Fmain%2Fkotlin%2Fcom%2Fwavesenterprise%2Fsdk%2Fnode%2Fclient%2Fblocking%2Fratelimit%2FRateLimitingServiceFactory.kt) - a wrapper for limiting requests to the node based on the UTX pool load and the provided settings.
   - [LoadBalancingServiceFactory](we-node-client-blocking-client%2Fsrc%2Fmain%2Fkotlin%2Fcom%2Fwavesenterprise%2Fsdk%2Fnode%2Fclient%2Fblocking%2Flb%2FLoadBalancingServiceFactory.kt) - a wrapper for HTTP load balancing when working with more than one WE blockchain node.
3. Provide the ability to choose from different types of transport to communicate with Blockchain Nodes: GRPC([GrpcNodeServiceFactory](we-node-client-grpc%2Fwe-node-client-grpc-blocking-client%2Fsrc%2Fmain%2Fkotlin%2Fcom%2Fwavesenterprise%2Fsdk%2Fnode%2Fclient%2Fgrpc%2Fblocking%2Ffactory%2FGrpcNodeServiceFactory.kt)) or HTTP([FeignNodeServiceFactory](we-node-client-http%2Fwe-node-client-feign-client%2Fsrc%2Fmain%2Fkotlin%2Fcom%2Fwavesenterprise%2Fsdk%2Fnode%2Fclient%2Ffeign%2Ffactory%2FFeignNodeServiceFactory.kt)).
## Modules
There are several modules in we-node-client. Here is a quick overview:

### [we-atomic](we-atomic)
The module responsible for working with atomic transactions (120).
The main element of which is [AtomicAwareNodeBlockingServiceFactory](we-atomic%2Fsrc%2Fmain%2Fkotlin%2Fcom%2Fwavesenterprise%2Fsdk%2Fatomic%2FAtomicAwareNodeBlockingServiceFactory.kt). 

[AtomicAwareNodeBlockingServiceFactory](we-atomic%2Fsrc%2Fmain%2Fkotlin%2Fcom%2Fwavesenterprise%2Fsdk%2Fatomic%2FAtomicAwareNodeBlockingServiceFactory.kt) - a wrapper which accepts [NodeBlockingServiceFactory](we-node-client-blocking-client%2Fsrc%2Fmain%2Fkotlin%2Fcom%2Fwavesenterprise%2Fsdk%2Fnode%2Fclient%2Fblocking%2Fnode%2FNodeBlockingServiceFactory.kt)  in its constructor.
The main task of this wrapper is:
* Intercepting the broadcast of transactions and collecting them in the AtomicContext instead;
* Building resulting AtomicTx with all the collected transactions;
* Signing and broadcasting atomic transaction;

### [we-node-client-blocking-client](we-node-client-blocking-client)
Interfaces for working with Node API in a blocking way and client wrappers. 

#### Interfaces: 
[TxService](we-node-client-blocking-client%2Fsrc%2Fmain%2Fkotlin%2Fcom%2Fwavesenterprise%2Fsdk%2Fnode%2Fclient%2Fblocking%2Ftx%2FTxService.kt),
[ContractService](we-node-client-blocking-client%2Fsrc%2Fmain%2Fkotlin%2Fcom%2Fwavesenterprise%2Fsdk%2Fnode%2Fclient%2Fblocking%2Fcontract%2FContractService.kt),
[AddressService](we-node-client-blocking-client%2Fsrc%2Fmain%2Fkotlin%2Fcom%2Fwavesenterprise%2Fsdk%2Fnode%2Fclient%2Fblocking%2Faddress%2FAddressService.kt),
[NodeInfoService](we-node-client-blocking-client%2Fsrc%2Fmain%2Fkotlin%2Fcom%2Fwavesenterprise%2Fsdk%2Fnode%2Fclient%2Fblocking%2Fnode%2FNodeInfoService.kt),
[PrivacyService](we-node-client-blocking-client%2Fsrc%2Fmain%2Fkotlin%2Fcom%2Fwavesenterprise%2Fsdk%2Fnode%2Fclient%2Fblocking%2Fprivacy%2FPrivacyService.kt),
[BlocksService](we-node-client-blocking-client%2Fsrc%2Fmain%2Fkotlin%2Fcom%2Fwavesenterprise%2Fsdk%2Fnode%2Fclient%2Fblocking%2Fblocks%2FBlocksService.kt),
[NodeUtilsService](we-node-client-blocking-client%2Fsrc%2Fmain%2Fkotlin%2Fcom%2Fwavesenterprise%2Fsdk%2Fnode%2Fclient%2Fblocking%2Futil%2FNodeUtilsService.kt).
#### Wrappers:
1. **RateLimitingServiceFactory** - wrapper for a single HTTP client that limits the load on the WE Blockchain Node;
2. **CachingNodeBlockingServiceFactory** - wrapper for efficient use of the node by storing a temporary cache.
3. **LoadBalancingServiceFactory** - wrapper for load balancing between nodes; 

 #### CachingNodeBlockingServiceFactory

Wraps specific services that work with transactions(**BlockService**, **TxService**) and privacy(**PrivacyService**). It is done in the following way:

  * **BlockService**: When receiving one(`block At Height({height})`) or several(`blockSequence({fromHeight}, {toHeight})`) blocks - all aligned transactions from the received blocks will be cached.

  * **TxService**: When a transaction is received (`txInfo({txId})`), if it was previously cached, it will be returned from the cache, or added to it, if it is missing. 

  * **PrivacyService**: When receiving policy item info (`info({policyItemRequest})`) caches the successful response.



### [we-node-client-domain](we-node-client-domain)
Domain models for Node.
### [we-node-client-error](we-node-client-error)
Exceptions thrown by node clients common for all implementations.

Each transport implementation (GRPC/HTTP) should provide mapping to these errors. 
For example this is how it is done in [FeignNodeErrorMapper](we-node-client-http%2Fwe-node-client-feign-client%2Fsrc%2Fmain%2Fkotlin%2Fcom%2Fwavesenterprise%2Fsdk%2Fnode%2Fclient%2Ffeign%2FFeignNodeErrorMapper.kt).
### [we-node-client-grpc](we-node-client-grpc)
Contains the implementation of a node client using a GRPC protocol.
### Instantiation example
Kotlin:
```kotlin
val grpcNodeServiceFactory: GrpcNodeServiceFactory = GrpcNodeServiceFactoryFactory.createClient(
    grpcProperties = GrpcNodeClientParams(
        address = "node-address",
        port = 8080,
        keepAliveTime = 5000,
        keepAliveWithoutCalls = true,
    )
)
```
Java:
```java
GrpcNodeServiceFactory grpcNodeServiceFactory = 
    GrpcNodeServiceFactoryFactory.createClient(
        new GrpcNodeClientParams("node-address", 8080, 5000L, true),
        null
    );
```

### [we-node-client-http](we-node-client-http)
Contains implementation of a node client using the HTTP protocol.

There can different implementations for this type of transport, at the moment only Feign implementation is available ([we-node-client-feign-client](we-node-client-http%2Fwe-node-client-feign-client)). 
#### Feign
More settings in [FeignNodeClientParams](we-node-client-http%2Fwe-node-client-feign-client%2Fsrc%2Fmain%2Fkotlin%2Fcom%2Fwavesenterprise%2Fsdk%2Fnode%2Fclient%2Ffeign%2FFeignNodeClientParams.kt).
##### Example of instantiation and usage
Kotlin:
```kotlin
val feignNodeBlockingServiceFactory = FeignNodeServiceFactory(
    params = FeignNodeClientParams(
        url = "https://localhost:8080",
        xApiKey = "xApiKey",
    )
)
```
Java:
```java
FeignNodeServiceFactory feignNodeBlockingServiceFactory = new FeignNodeServiceFactory(
    new FeignNodeClientParams(
        "https://locaclhost:8080",
        "xApiKey"
    )
);
```
### [we-node-client-json](we-node-client-json)
Module for mapping domain objects to DTO and vice versa.

### [we-node-domain-test](we-node-domain-test)
Module for creating pre-filled test objects. Consists of static methods in the [TestDataFactory](we-node-domain-test%2Fsrc%2Fmain%2Fkotlin%2Fcom%2Fwavesenterprise%2Fsdk%2Fnode%2Ftest%2Fdata%2FTestDataFactory.kt).
### Example
Kotlin:
```kotlin
val createContractTx: CreateContractTx = TestDataFactory.createContractTx()
```

### [we-tx-signer-api](we-tx-signer%2Fwe-tx-signer-api)
Interface fo signing transactions before broadcasting them to WE Node. 
Implementations should populate SignRequest with ID and proofs in order to create a signed Tx.
### [we-tx-signer-node](we-tx-signer%2Fwe-tx-signer-node)
Implementation that signs transactions using WE Node REST API with key pairs stored in WE Node KeyStore.
### Example of instantiation and usage
Kotlin:
```kotlin
// creating NodeBlockingServiceFactory for getting TxService
val nodeBlockingServiceFactory = FeignNodeServiceFactory(FeignNodeClientParams("url"))
// creating TxService for TxSigner
val txService: TxService = nodeBlockingServiceFactory.txService()
// setting credentials
val credentials = Credentials(
    senderAddress = "senderAddress".base58Address,
    password = "password".password,
)
// creating TxSigner with credentials
val txSigner: TxSigner = TxServiceTxSignerFactory(txService = txService).withCredentials(credentials = credentials)
// method sign() will return signed Tx
txSigner.sign(TestDataFactory.createContractSignRequest())
```
Java:
```java
// creating NodeBlockingServiceFactory for getting TxService
NodeServiceFactory nodeBlockingServiceFactory = new FeignNodeServiceFactory(new FeignNodeClientParams("url"));
// creating TxService for TxSigner
TxService txService = nodeBlockingServiceFactory.txService();
// setting credentials
Credentials credentials = new Credentials(
    "senderAddress".base58Address,
    "password".password
);
// creating TxSigner with credentials
TxSigner txSigner = new TxServiceTxSignerFactory(txService).withCredentials(credentials);
// method sign() will return signed Tx
txSigner.sign(TestDataFactory.createContractSignRequest());
```

## Example showing adding the dependency, creating and using a client
Gradle:
```
implementation("com.wavesenterprise:we-node-client-feign-client:$version")
implementation("com.wavesenterprise:we-node-client-grpc-client:$version")
```
Maven:
```
<dependency>
    <groupId>com.wavesenterprise</groupId>
    <artifactId>we-node-client-feign-client</artifactId>
    <version>${version}</version>
</dependency>
<dependency>
    <groupId>com.wavesenterprise</groupId>
    <artifactId>we-node-client-grpc-client</artifactId>
    <version>${version}</version>
</dependency>
```
### Example of using NodeBlockingServiceFactory
```kotlin
fun example() {
    // create node client with http or grpc connection
    val nodeBlockingServiceFactory: NodeBlockingServiceFactory
    // get necessary service 
    val txService: TxService = nodeBlockingServiceFactory.txService()
    // use service (API of the interfaces matches the api of the node)
    val txInfo: TxInfo = txService.txInfo({txId})
}
```
```java
public void example() {
    // create node client with http or grpc connection
    NodeBlockingServiceFactory nodeBlockingServiceFactory;
    // get necessary service 
    TxService txService = nodeBlockingServiceFactory.txService();
    // use service (API of the interfaces matches the api of the node)
    TxInfo txInfo = txService.txInfo({txId});
}
```
### Example of wrappers instantiation
#### RateLimitingServiceFactory
```kotlin
fun example() {
    // default client with http or grpc connection
    val nodeBlockingServiceFactory = NodeBlockingServiceFactory
    // wrapped rate limiting client 
    val rateLimitingClient = RateLimitingServiceFactory(
        nodeBlockingServiceFactory = nodeBlockingServiceFactory,
        rateLimiter = DefaultRateLimiter(
            strategy = UtxPoolSizeLimitingStrategy(
                txService = nodeBlockingServiceFactory.txService(),
                maxUtx = 50,
            ),
            backOff = RandomDelayRateLimitingBackOff(
                minWaitMs = 1000,
                maxWaitMs = 3000,
                maxWaitTotalMs = 10000,
            )
        )
    )
}
```
```java
public void example() {
        // default client with http or grpc connection
        NodeBlockingServiceFactory nodeBlockingServiceFactory;
        // wrapped rate limiting client 
        RateLimitingServiceFactory rateLimitingClient = RateLimitingServiceFactory(
            nodeBlockingServiceFactory,
            new DefaultRateLimiter(
                    new UtxPoolSizeLimitingStrategy(nodeBlockingServiceFactory.txService(), 50),
                    new RandomDelayRateLimitingBackOff(1000, 3000, 10000)
            )
        );
}
```
#### LoadBalancingServiceFactory
```kotlin
fun example() {
    // default clients with http or grpc connection
    val nodeBlockingServiceFactories: List<NodeBlockingServiceFactory>
    // wrapped load balancing client
    val lbClient: LoadBalancingServiceFactory = LbServiceFactoryBuilder.builder().build(nodeBlockingServiceFactories)
}
```
```java
public void example(){
    // default clients with http or grpc connection
    List<NodeBlockingServiceFactory> nodeBlockingServiceFactories;
    // wrapped load balancing client
    LoadBalancingServiceFactory lbClient=LbServiceFactoryBuilder.builder().build(nodeBlockingServiceFactories);
}
```
#### CachingNodeBlockingServiceFactory
```kotlin
fun example() {
    // default clients with http or grpc connection
    val nodeBlockingServiceFactory: NodeBlockingServiceFactory
    // wrapped caching client
    val cachingClient = CachingNodeBlockingServiceFactoryBuilder.builder().build(nodeBlockingServiceFactory) 
    
}
```
```java
public void example(){
    // default clients with http or grpc connection
    List<NodeBlockingServiceFactory> nodeBlockingServiceFactories;
    // wrapped caching client
    CachingNodeBlockingServiceFactoryBuilder cachingClient = CachingNodeBlockingServiceFactoryBuilder.builder().build(nodeBlockingServiceFactories);
}
```

## Links:
* [Waves Enterprise documentation](https://docs.wavesenterprise.com/ru/latest/)
