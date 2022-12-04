package object;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Excalinyan extends SuperObject{
public Excalinyan(){

	name = "Excalinyan";
	try{
	    image = ImageIO.read(getClass().getResourceAsStream("/res/objects/excalinyan.png"));
	}catch(IOException e){
	    e.printStackTrace();
	}
	collision = true;
    }
}
