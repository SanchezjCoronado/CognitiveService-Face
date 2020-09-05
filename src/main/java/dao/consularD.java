/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import model.consultaM;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

/**
 *
 * @author deivy
 */
public class consularD {

    public consultaM consultar(String url) throws IOException {
        consultaM variablesModel = new consultaM();
        HttpClient httpClient = new DefaultHttpClient();
        try {
            StringEntity body = new StringEntity("{\"Url\": \"" + url + "\"}");
            JsonParser converter = new JsonParser();
            String emocion[] = "anger,contempt,disgust,fear,happiness,neutral,sadness,surprise".split(",");
            HttpPost request = new HttpPost("https://democs.cognitiveservices.azure.com/face/v1.0/detect?returnFaceId=true&returnFaceLandmarks=false&recognitionModel=recognition_01&returnRecognitionModel=false&detectionModel=detection_01&returnFaceAttributes=age,gender,smile,facialHair,glasses,emotion,hair,makeup,occlusion,accessories");
            request.addHeader("Ocp-Apim-Subscription-Key", "73770c61b20c48c7a9583529ad946e9e");
            request.addHeader("Content-Type", "application/json");
            request.setEntity(body);
            HttpResponse response = httpClient.execute(request);
            HttpEntity entity = response.getEntity();
            JsonArray array = converter.parse(EntityUtils.toString(entity)).getAsJsonArray();
            JsonObject object = array.get(0).getAsJsonObject();
            JsonObject attributes = object.getAsJsonObject("faceAttributes");
            JsonObject emotion = attributes.getAsJsonObject("emotion");
            JsonObject velloCara = attributes.getAsJsonObject("facialHair");
            JsonObject maquillaje = attributes.getAsJsonObject("makeup");
            JsonObject pelo = attributes.getAsJsonObject("hair");
            JsonArray colorPelo = pelo.getAsJsonArray("hairColor");
            String lentes = attributes.get("glasses").getAsString();
            String edad = attributes.get("age").getAsString();
            String genero = attributes.get("gender").getAsString();
            double bigote = velloCara.get("moustache").getAsDouble();
            double barba = velloCara.get("beard").getAsDouble();
            double patillas = velloCara.get("sideburns").getAsDouble();
            String ojosPintados = maquillaje.get("eyeMakeup").getAsString();
            String labiosPintados = maquillaje.get("lipMakeup").getAsString();
            double calvo = pelo.get("bald").getAsDouble();
            String invisible = pelo.get("invisible").getAsString();
            String nameEmotion = null;
            int valueEmotion = 0;
            for (String valores : emocion) {
                if (valueEmotion < emotion.get(valores).getAsInt()) {
                    nameEmotion = valores;
                    valueEmotion = emotion.get(valores).getAsInt();
                }
            }
            JsonObject colores = null;
            for (JsonElement jsonElement : colorPelo) {
                if (colores == null) {
                    colores = jsonElement.getAsJsonObject();
                } else {
                    if (colores.get("confidence").getAsDouble() < jsonElement.getAsJsonObject().get("confidence").getAsDouble()) {
                        colores = jsonElement.getAsJsonObject();
                    }
                }
            }
            String colorPerlo = colores.get("color").getAsString();
//            System.out.println(json1);
            variablesModel.setEMOCION(nameEmotion);
            variablesModel.setBIGOTE(bigote > 0.5 ? "SÍ" : "NO");
            variablesModel.setBARBA(barba > 0.5 ? "SÍ" : "NO");
            variablesModel.setPATILLAS(patillas > 0.5 ? "SÍ" : "NO");
            variablesModel.setGAFAS(lentes.equals("NoGlasses") ? "SIN LENTES" : "CON LENTES");
            variablesModel.setAge(edad);
            variablesModel.setGender(genero.equals("female") ? "MUJER" : "HOMBRE");
            variablesModel.setOJOSMAQUILLADOS(ojosPintados.equals("false") ? "NO" : "SÍ");
            variablesModel.setLABIOSPINTADOS(labiosPintados.equals("false") ? "NO" : "SÍ");
            variablesModel.setCALVO(calvo > 0.5 ? "SÍ" : "NO");
            variablesModel.setINVENSIBLE(invisible.equals("false") ? "NO" : "SÍ");
            variablesModel.setCOLORPELO(colorPerlo);
            System.out.println("Emoción: " + nameEmotion);
            System.out.println("Bigote: " + bigote);
            System.out.println("Barba: " + barba);
            System.out.println("Patillas: " + patillas);
            System.out.println("lentes: " + lentes);
            System.out.println("edad: " + edad);
            System.out.println("Género: " + genero);
            System.out.println("Ojos Pintados: " + ojosPintados);
            System.out.println("Labios Pintados: " + labiosPintados);
            System.out.println("Calvo: " + calvo);
            System.out.println("Invisible: " + invisible);
            System.out.println("Color de pelo: " + colorPerlo);
        } catch (IOException e) {
            throw e;
        }
        return variablesModel;
    }
}
