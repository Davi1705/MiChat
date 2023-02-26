package com.example.michat;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    @FXML
    private Button btnSend;
    @FXML
    private ImageView imgUser;
    @FXML
    private Label lblUser;
    @FXML
    private ScrollPane spChat;
    @FXML
    private TextField tfMsg;
    @FXML
    private VBox vbChat;

    private Client client;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try{
            client = new Client(new Socket("LocalHost", 3000));
            System.out.println("Conectado al servidor");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        vbChat.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                spChat.setVvalue((Double) t1);
            }
        });

        client.receiveMsg(vbChat);

        btnSend.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String msgToSend = tfMsg.getText();
                if (!msgToSend.isEmpty()){
                    HBox hBox = new HBox();
                    hBox.setAlignment(Pos.CENTER_RIGHT);
                    hBox.setPadding(new Insets(5, 5, 5, 10));

                    Text txt = new Text(msgToSend);
                    TextFlow txtFlow = new TextFlow(txt);

                    txtFlow.setPadding(new Insets(5, 10, 5, 10));
                    txt.setFill(Color.color(0.934, 0.945, 0.996));

                    hBox.getChildren().add(txtFlow);
                    vbChat.getChildren().add(hBox);

                    client.sendMsgServer(msgToSend);
                    tfMsg.clear();
                }
            }
        });
    }

    public static void addLabel(String msgServer, VBox vBox){
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setPadding(new Insets(5, 5, 5, 10));

        Text txt = new Text(msgServer);
        TextFlow txtFlow = new TextFlow(txt);
        txtFlow.setPadding(new Insets(5, 10, 5, 10));
        hBox.getChildren().add(txtFlow);

        Platform.runLater(new Runnable() {

            @Override
            public void run() {
                vBox.getChildren().add(hBox);
            }
        });
    }


}