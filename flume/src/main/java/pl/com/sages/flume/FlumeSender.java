package pl.com.sages.flume;

public class FlumeSender {

    public static void main(String[] args) {

        FlumeRpcClientFacade client = new FlumeRpcClientFacade();
        client.init("localhost", 4444);

        String sampleData = "Hello Flume ";
        for (int i = 0; i < 10; i++) {
            client.sendDataToFlume(sampleData + i);
        }

        client.cleanUp();

    }

}
