package util;

public class Noise {
    private double[][] noiseMap;
    private int width;
    private int height;

    public Noise(int width, int height) {
        this.width = width;
        this.height = height;
        noiseMap = new double[height][width];
    }

    public void generateNoise(double frequency) {
        for(int y = 0; y < height; y++) {
            for(int x = 0; x < width; x++) {
                double noiseValue = 0.0;
    
                for(int octave = 0; octave < 8; octave++) {
                    double octaveFrequency = frequency * Math.pow(2, octave);
                    double amplitude = Math.pow(0.5, octave);
    
                    double sampleX1 = (double)x / octaveFrequency;
                    double sampleY1 = (double)y / octaveFrequency;
                    double sampleX2 = sampleX1 + 1000;
                    double sampleY2 = sampleY1 + 1000;
    
                    double noise1 = interpolate(
                        smoothNoise((int)sampleX1, (int)sampleY1),
                        smoothNoise((int)sampleX1 + 1, (int)sampleY1),
                        sampleX1 - (int)sampleX1
                    );
                    double noise2 = interpolate(
                        smoothNoise((int)sampleX1, (int)sampleY1 + 1),
                        smoothNoise((int)sampleX1 + 1, (int)sampleY1 + 1),
                        sampleX1 - (int)sampleX1
                    );
                    double noise3 = interpolate(
                        smoothNoise((int)sampleX2, (int)sampleY2),
                        smoothNoise((int)sampleX2 + 1, (int)sampleY2),
                        sampleX2 - (int)sampleX2
                    );
                    double noise4 = interpolate(
                        smoothNoise((int)sampleX2, (int)sampleY2 + 1),
                        smoothNoise((int)sampleX2 + 1, (int)sampleY2 + 1),
                        sampleX2 - (int)sampleX2
                    );
    
                    double noiseValueOctave = (noise1 + noise2 + noise3 + noise4) * amplitude;
                    noiseValue += noiseValueOctave;
                }
    
                noiseMap[y][x] = noiseValue;
            }
        }
    }

    private double smoothNoise(int x, int y) {
        double corners = (noise(x - 1, y - 1) + noise(x + 1, y - 1) + noise(x - 1, y + 1) + noise(x + 1, y + 1)) / 16.0;
        double sides = (noise(x - 1, y) + noise(x + 1, y) + noise(x, y - 1) + noise(x, y + 1)) / 8.0;

        double center = noise(x, y) / 4.0;

        return corners + sides + center;
    }

    private double noise(int x, int y) {
        int n = x + y * 57;
        n = (n << 13) ^ n;

        return (1.0 - ((n * (n * n * 15731 + 789221) + 1376312589) & 0x7fffffff) / 1073741824.0);
    }

    private double interpolate(double a, double b, double x) {
        double ft = x * Math.PI;
        double f = (1 - Math.cos(ft)) * 0.5;

        return a * (1 - f) + b * f;
    }

    public double[][] getNoise() {
        return noiseMap;
    }

    public double findQuartile(int quartile) {
        double[] noiseArray = new double[width * height];
        int index = 0;

        for(int y = 0; y < height; y++) {
            for(int x = 0; x < width; x++) {
                noiseArray[index] = noiseMap[y][x];
                index++;
            }
        }

        double[] sortedNoiseArray = sort(noiseArray);

        if(quartile == 1) {
            return sortedNoiseArray[sortedNoiseArray.length / 4];
        } else if(quartile == 2) {
            return sortedNoiseArray[sortedNoiseArray.length / 2];
        } else if(quartile == 3) {
            return sortedNoiseArray[sortedNoiseArray.length / 4 * 3];
        } else {
            return 0;
        }
    }

    private double[] sort(double[] noiseArray) {
        for(int i = 0; i < noiseArray.length; i++) {
            for(int j = 0; j < noiseArray.length - 1; j++) {
                if(noiseArray[j] > noiseArray[j + 1]) {
                    double temp = noiseArray[j];
                    noiseArray[j] = noiseArray[j + 1];
                    noiseArray[j + 1] = temp;
                }
            }
        }

        return noiseArray;
    }
    
}
