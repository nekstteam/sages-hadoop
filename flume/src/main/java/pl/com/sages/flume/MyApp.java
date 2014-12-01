package pl.com.sages.flume;

public class MyApp {

    public static void main(String[] args) {

        MyRpcClientFacade client = new MyRpcClientFacade();
        client.init("localhost", 41414);

        String sampleData = "Hello Flume!";
        for (int i = 0; i < 10; i++) {
            client.sendDataToFlume(sampleData);
        }

        client.cleanUp();

    }

}
