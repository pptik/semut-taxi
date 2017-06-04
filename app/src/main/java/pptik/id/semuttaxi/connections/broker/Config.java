package pptik.id.semuttaxi.connections.broker;


import pptik.id.semuttaxi.setup.Constant;

public class Config {
    public static final String hostName = Constant.MQ_HOSTNAME;
    public static final String virtualHostname = Constant.MQ_VIRTUAL_HOST;
    public static final String username = Constant.MQ_USERNAME;
    public static final String password = Constant.MQ_PASSWORD;
    public static final int port = Constant.MQ_PORT;
    public static final String exchange = Constant.MQ_EXCHANGE_NAME;
    public static final String rotuingkey = Constant.MQ_DEFAULT_ROUTING_KEY;
    public static final String queuename = Constant.MQ_EXCHANGE_NAME;
}