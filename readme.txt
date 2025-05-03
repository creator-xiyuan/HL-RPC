一个 RPC 轮子项目，模块如下：
    consumer：服务消费者
    provider：服务提供者
    common：存放 consumer 和 provider 共用的类（本来应该放在 provider 的 api 包下，但本项目较小，没有拆分）
    rpc：rpc 基础功能
    rpcV1：rpc 拓展版 V1