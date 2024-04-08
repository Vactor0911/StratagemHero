package stratagemhero;

import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageProducer;
import java.awt.image.RGBImageFilter;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

class ColorizeFilter extends RGBImageFilter implements Serializable {
    private static final long serialVersionUID = 1L;
    private int color;

    public ColorizeFilter(int color) {
        this.color = color;
        canFilterIndexColorModel = true;
    }

    public static Image colorizeImg(Image img, Color color) {
        RGBImageFilter filter = new ColorizeFilter(color.getRGB());
        ImageProducer producer = new FilteredImageSource(img.getSource(), filter);
        Image coloredImg = Toolkit.getDefaultToolkit().createImage(producer);
        return coloredImg;
    }

    @Override
    public int filterRGB(int x, int y, int rgb) {
        int alpha = (rgb >> 24) & 0xFF;
        int red = (color >> 16) & 0xFF;
        int green = (color >> 8) & 0xFF;
        int blue = color & 0xFF;
        return (alpha << 24) | (red << 16) | (green << 8) | blue;
    }
} // ColorizeFilter Class

class Stratagem {
    private HashMap<String, JSONObject> dictStratagem = new HashMap<>();
    private HashMap<String, String[]> dictCategory = new HashMap<>();

    public Stratagem() {
        try {
            InputStream is = this.getClass().getResourceAsStream("/Stratagems.json");
            String content = new String(is.readAllBytes());
            is.close();
            JSONArray aryJson = new JSONArray(content);

            // Read all JSON data
            HashMap<String, ArrayList<String>> dict = new HashMap<>();
            for (Object k : aryJson) {
                JSONObject obj = (JSONObject) k;

                // Exclude invalid data
                if (obj.getString("command").isBlank() || obj.getString("image").isBlank()) {
                    continue;
                }

                dictStratagem.put(obj.getString("name"), obj);

                ArrayList<String> list = dict.get(obj.getString("category"));
                if (list == null) {
                    list = new ArrayList<>();
                }
                list.add(obj.getString("name"));
                dict.put(obj.getString("category"), list);
            }

            // Initialize dictionary
            for (String k : dict.keySet()) {
                String[] array = dict.get(k).toArray(new String[0]);
                dictCategory.put(k, array);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    } // Constructor

    public JSONObject[] getRandStratagem(int count, String[] filter) {
        JSONObject[] aryObject = new JSONObject[count];

        ArrayList<String> list = new ArrayList<>();

        // Set Filter
        String[] fixedFilter = filter;
        if (fixedFilter == null) {
            fixedFilter = dictCategory.keySet().toArray(new String[0]);
        }

        // Init stratagem array by filter
        for (String k : fixedFilter) {
            String[] array = dictCategory.get(k);
            if (array == null) {
                continue;
            }
            for (String l : array)
                list.add(l);
        }
        Collections.shuffle(list); // Shuffle stratagem list

        // Put random stratagems
        for (int i = 0; i < count; i++) {
            int randInt = (int) (Math.random() * list.size());
            String name = list.get(randInt);
            aryObject[i] = dictStratagem.get(name);
        }

        return aryObject;
    } // getRandStratagem()

    public JSONObject[] getRandStratagem(int count) {
        return getRandStratagem(count, null);
    } // getRandStratagem()
} // Stratagem Class

class SoundPlayer {
    private HashMap<String, URL> dictUrl = new HashMap<>();
    private Clip clip;

    public SoundPlayer() {
        try {
            clip = AudioSystem.getClip();
        }
        catch (Exception e) {}
    }

    public void playSound(String path, int loop) {
        
        URL soundUrl = dictUrl.get(path);
        if (soundUrl == null) {
            soundUrl = Main.getPath(path);
            dictUrl.put(path, soundUrl);
        }

        try{
            if ( clip.isOpen() ) {
                clip.stop();
                clip.flush();
            }

            clip = AudioSystem.getClip();
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundUrl);
            clip.open(audioIn);
            clip.loop(loop);

            //Adjust Volume
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-10.0f);

            clip.start();
        }
        catch (Exception e) {}
    }

}