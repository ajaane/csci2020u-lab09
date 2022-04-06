package example.lab09;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HelloController {
    @FXML
    public Canvas canvas;

    public void initialize() throws IOException {
        downloadStockPrices();
    }

    Integer num = 0;
    Double scale, range;

    private void downloadStockPrices() throws IOException {
        // Google
        String goog = "https://query1.finance.yahoo.com/v7/finance/download/GOOG?period1=1262322000&period2=1451538000&interval=1mo&eve" +
                "nts=history&includeAdjustedClose=true";
        URL url = new URL(goog);
        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));

        String[] parts;
        List<Float> googData = new ArrayList<>();
        String line;
        reader.readLine();
        while ((line = reader.readLine()) != null) {
            parts = line.split(",");
            googData.add(Float.parseFloat(parts[4]));
        }
        reader.close();


        // Apple
        String amzn = "https://query1.finance.yahoo.com/v7/finance/download/AMZN?period1=1262322000&period2=1451538000&interval=1mo&eve" +
                "nts=history&includeAdjustedClose=true";
        URL urlA = new URL(amzn);
        BufferedReader readerA = new BufferedReader(new InputStreamReader(urlA.openStream()));


        String[] partsA;
        List<Float> amznData = new ArrayList<>();
        readerA.readLine();
        while ((line = readerA.readLine()) != null) {
            partsA = line.split(",");
            amznData.add(Float.parseFloat(partsA[4]));
        }
        reader.close();



        drawLinePlot(googData, amznData);
    }

    private void drawLinePlot(List<Float> googData, List<Float> amznData){
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.setStroke(Color.BLACK);
        gc.strokeLine(50, 430, 590, 430);
        gc.strokeLine(50, 50, 50, 430);

        plotLine(googData, gc);

        plotLine(amznData, gc);

    }

    private void plotLine(List<Float> data, GraphicsContext gc){

        double max = (double) data.get(0);
        double min = (double) data.get(0);
        for (float element : data){
            if (max < element)
                max = (double) element;
            if (min > element)
                min = (double) element;
        }
        range = max - min;
        scale = 380/range;

        if (num == 0){
            gc.setStroke(Color.BLUE);

        }
        else {
            gc.setStroke(Color.RED);
        }


        Integer size = data.size();
        System.out.println(size);
        double x = 50.0;
        int increment = 540/ size;
        for (int i = 0; i < data.size() -1; i++){
            gc.strokeLine(x,455 - ((double) (data.get(i)) * scale), x + increment, 455 - ((double)(data.get(i+1)) * scale));
            x += increment;
        }
        num++;
    }



}
