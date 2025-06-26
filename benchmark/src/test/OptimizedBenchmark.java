package test;

import client.TestClient;
import commands.CommandHandler;
import commands.HistoryCommand;
import network.Request;
import network.Response;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
@Warmup(iterations = 3, time = 5, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 10, time = 30, timeUnit = TimeUnit.SECONDS)
@Threads(10)
@Fork(3)
@State(Scope.Thread)
public class OptimizedBenchmark {

    private TestClient client;
    private List<Request> requests;

    @Setup(Level.Trial)
    public void Setup() throws IOException{
        client = new TestClient();
        client.connect();
        HistoryCommand historyCommand = new HistoryCommand();

        CommandHandler commandHandler = new CommandHandler(null, historyCommand);

        requests = commandHandler.executeScript(ThreadLocalRandom.current().nextInt(1, 11));
    }

    @Benchmark
    public void benchmarkSingleRequest(Blackhole blackhole) {
        Request request = requests.get(0);

        try {
            client.sendRequest(request);
            Response response = client.receiveResponse();
            blackhole.consume(response);
        } catch (Exception e) {
            throw new RuntimeException("Request failed", e);
        }
    }

    @TearDown(Level.Trial)
    public void tearDown() throws IOException {
        if (client.getSocketChannel() == null && !client.getSocketChannel().isOpen()) {
            client.getSocketChannel().close();
        }
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(OptimizedBenchmark.class.getSimpleName())
                .result("results.json")
                .resultFormat(ResultFormatType.JSON)
                .output("jmh_output.log")
                .build();

        new Runner(opt).run();
    }
}