# what's this?
    
    This is a minimal implementation of an NFT trading platform based on the FISCO BCOS blockchain platform and Spring Boot,with NFTs adhering to standards such as ERC720.
    这是一个基于FISCO BCOS区块链平台和Spring Boot的NFT交易平台的最小实现。NFT采用了ERC720等标准。
# How To Builder?

1. Install the [go-ipfs](https://docs.ipfs.io/introduction/install/ ),And start IPFS Client, 
2. Configure the ipfs.vps.node section of the yml file， for example: 
```yaml
ipfs:
  vps:
    node: /ip4/8.129.91.132/tcp/5001
```

# 待完善内容
1. 所有SQL操作事务化
2. 消息队列的使用逻辑(由区块链执行相应方法 >> RabbitMQ >> Service类执行方法,改为 Service类执行相应方法 >> RabbitMQ >>区块链执行相应方法)