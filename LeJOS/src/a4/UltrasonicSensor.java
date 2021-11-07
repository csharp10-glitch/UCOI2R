package a4;

import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;

public class UltrasonicSensor {
    EV3UltrasonicSensor sensor;
    SampleProvider distMode;
    SampleProvider average;
    int distance;
    float[] sample;
    private final int DISTANCE_THRESHOLD = 0;

    public UltrasonicSensor(Port s1) {
        sensor = new EV3UltrasonicSensor(s1);
        distMode = sensor.getMode("Distance");
        distance = 255;
//        average = new MeanFilter(distMode, 5);
//        sample = new float[average.sampleSize()];
//        average.fetchSample(sample, 0);
    }

    public int distance() {
        float [] sample = new float[distMode.sampleSize()];
        distMode.fetchSample(sample, 0);
        distance = (int)(sample[0]*100);
        System.out.println(distance);
        return distance;
    }

    public void checkForWall() throws WallEncounteredException
    {
        if(distance() <= DISTANCE_THRESHOLD)
        {
            throw new WallEncounteredException("Wall Encountered!");
        }
    }

//    ir.close();
    public class WallEncounteredException extends Exception
    {
        public WallEncounteredException(String errorMessage)
        {
            super(errorMessage);
        }
    }
}