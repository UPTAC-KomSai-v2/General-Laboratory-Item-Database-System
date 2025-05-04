import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ImageStorage {

    private final Map<String, String> imagePaths;
    private final String basePath = "Assets/ItemImages/";
    private final String extension = ".jpg";

    public ImageStorage() {
        imagePaths = new HashMap<>();
        initializeImagePaths();
    }

    private void initializeImagePaths() {
        String[][] items = {
            {"Analytical Balance", "AnalyticalBalance"},
            {"Beaker", "Beaker"},
            {"Bunsen Burner", "BunsenBurner"},
            {"Burette", "Burette"},
            {"Burette Clamp", "BuretteClamp"},
            {"Centrifuge", "Centrifuge"},
            {"Centrifuge Tube", "CentrifugeTube"},
            {"Conductivity Meter", "ConductivityMeter"},
            {"Cryovial", "Cryovial"},
            {"Culture Flask", "CultureFlask"},
            {"Culture Media", "CultureMedia"},
            {"Cuvette", "Cuvette"},
            {"Desiccator", "Desiccator"},
            {"Electrophoresis Apparatus", "ElectrophoresisApparatus"},
            {"Forcep", "Forcep"},
            {"Funnel", "Funnel"},
            {"Glass Rod", "GlassRod"},
            {"Graduated Cylinder", "GraduatedCylinder"},
            {"Hot Plate", "HotPlate"},
            {"Inoculation Loop", "InoculationLoop"},
            {"Laboratory Coat", "LaboratoryCoat"},
            {"Microscope", "Microscope"},
            {"Multimeter", "Multimeter"},
            {"Petri Dish", "PetriDish"},
            {"pH Meter", "pHMeter"},
            {"Pipettes", "Pipettes"},
            {"Reagents Bottle", "ReagentsBottle"},
            {"Refractometer", "Refractometer"},
            {"Safety Goggles", "SafetyGoggles"},
            {"Scalpel", "Scalpel"},
            {"Spatula", "Spatula"},
            {"Spectrophotometer", "Spectrophotometer"},
            {"Test Tube", "TestTube"},
            {"Test Tube Clamp", "TestTubeClamp"},
            {"Test Tube Rack", "TestTubeRack"},
            {"Thermometer", "Thermometer"},
            {"Tong", "Tong"},
            {"Triple Beam Balance", "TripleBeamBalance"},
            {"Tweezers", "Tweezers"},
            {"Volumetric Flask", "VolumetricFlask"},
            {"Wash Bottles", "WashBottles"},
            {"Watch Glass", "WatchGlass"}
        };

        for (String[] item : items) {
            setImagePath(item[0], item[1]);
        }
    }

    public void setImagePath(String itemName, String fileName) {
        imagePaths.put(itemName, basePath + fileName + extension);
    }

    public String getImagePath(String itemName) {
        return imagePaths.get(itemName);
    }

    public Set<String> getAllItemNames() {
        return imagePaths.keySet();
    }

    public Map<String, String> getAllImagePaths() {
        return new HashMap<>(imagePaths);
    }
}
