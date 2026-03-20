package object;

import java.io.IOException;

import javax.imageio.ImageIO;

public class ObjectDoor extends SuperObject {
    public ObjectDoor() {
        name = "Key";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/objects/door.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
