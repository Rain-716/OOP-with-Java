import java.awt.Color;
import java.util.*;
public class Image {
    public static void printImageDimensions(String filename) {
        Picture pic = new Picture(filename);
        System.out.println("图片宽度: " + pic.width());
        System.out.println("图片高度: " + pic.height());
    }
    public static Picture convertToGrayscale(String filename) {
        Picture pic = new Picture(filename);
        int width = pic.width();
        int height = pic.height();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Color c = pic.get(x, y);
                int r = c.getRed();
                int g = c.getGreen();
                int b = c.getBlue();
                int grayVal = (int)(0.299 * r + 0.587 * g + 0.114 * b);
                Color gray = new Color(grayVal, grayVal, grayVal);
                pic.set(x, y, gray);
            }
        }
        return pic;
    }
    public static Picture resizePicture(String filename, int newWidth, int newHeight) {
        Picture original = new Picture(filename);
        Picture resized = new Picture(newWidth, newHeight);
        int origWidth = original.width();
        int origHeight = original.height();
        double xScale = (double) origWidth / newWidth;
        double yScale = (double) origHeight / newHeight;
        for (int x = 0; x < newWidth; x++) {
            for (int y = 0; y < newHeight; y++) {
                int origX = (int)(x * xScale);
                int origY = (int)(y * yScale);
                Color c = original.get(origX, origY);
                resized.set(x, y, c);
            }
        }
        return resized;
    }
    public static Picture[] createGradientPictures(String filename, int N) {
        Picture colorPic = new Picture(filename);
        Picture grayPic = convertToGrayscale(filename);
        int width = colorPic.width();
        int height = colorPic.height();
        Picture[] results = new Picture[N];
        for (int n = 0; n < N; n++) {
            double alpha = (double) n / (N - 1);
            Picture blended = new Picture(width, height);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    Color c1 = colorPic.get(x, y);
                    Color c2 = grayPic.get(x, y);
                    int r = (int)((1 - alpha) * c1.getRed()   + alpha * c2.getRed());
                    int g = (int)((1 - alpha) * c1.getGreen() + alpha * c2.getGreen());
                    int b = (int)((1 - alpha) * c1.getBlue()  + alpha * c2.getBlue());
                    blended.set(x, y, new Color(r, g, b));
                }
            }
            results[n] = blended;
        }
        return results;
    }
    public static Picture cropPicture(String filename, double xPercent, double yPercent, double ePercent) {
        Picture original = new Picture(filename);
        int origWidth = original.width();
        int origHeight = original.height();
        int centerX = (int)(origWidth * (xPercent / 100.0));
        int centerY = (int)(origHeight * (yPercent / 100.0));
        int side = (int)(origWidth * (ePercent / 100.0));
        int x0 = centerX - side / 2;
        int y0 = centerY - side / 2;
        if (x0 < 0) x0 = 0;
        if (y0 < 0) y0 = 0;
        if (x0 + side > origWidth) side = origWidth - x0;
        if (y0 + side > origHeight) side = origHeight - y0;
        Picture cropped = new Picture(side, side);
        for (int x = 0; x < side; x++) {
            for (int y = 0; y < side; y++) {
                cropped.set(x, y, original.get(x0 + x, y0 + y));
            }
        }
        return cropped;
    }
    public static Picture mirrorY(String filename) {
        Picture original = new Picture(filename);
        int width = original.width();
        int height = original.height();
        Picture mirrored = new Picture(width, height);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                mirrored.set(width - 1 - x, y, original.get(x, y));
            }
        }
        return mirrored;
    }
    public static Picture applyConvolutionFilter(Picture pic, double[][] kernel) {
        int width = pic.width();
        int height = pic.height();
        Picture output = new Picture(width, height);
        int kSize = kernel.length;
        int offset = kSize / 2;
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                double sumR = 0, sumG = 0, sumB = 0;
                for (int i = -offset; i <= offset; i++) {
                    for (int j = -offset; j <= offset; j++) {
                        int xi = x + i;
                        int yj = y + j;
                        if (xi < 0 || xi >= width || yj < 0 || yj >= height) continue;
                        Color c = pic.get(xi, yj);
                        double weight = kernel[i + offset][j + offset];
                        sumR += weight * c.getRed();
                        sumG += weight * c.getGreen();
                        sumB += weight * c.getBlue();
                    }
                }
                int r = Math.min(255, Math.max(0, (int) Math.round(sumR)));
                int g = Math.min(255, Math.max(0, (int) Math.round(sumG)));
                int b = Math.min(255, Math.max(0, (int) Math.round(sumB)));
                output.set(x, y, new Color(r, g, b));
            }
        }
        return output;
    }
    public static Picture linearFilter(String filename) {
        double[][] kernel = {
            {1.0/9, 1.0/9, 1.0/9},
            {1.0/9, 1.0/9, 1.0/9},
            {1.0/9, 1.0/9, 1.0/9}
        };
        Picture pic = new Picture(filename);
        return applyConvolutionFilter(pic, kernel);
    }
    public static Picture blurFilter(String filename) {
        double[][] kernel = {
            {1.0/13, 1.0/13, 1.0/13},
            {1.0/13, 5.0/13, 1.0/13},
            {1.0/13, 1.0/13, 1.0/13}
        };
        Picture pic = new Picture(filename);
        return applyConvolutionFilter(pic, kernel);
    }
    public static Picture embossFilter(String filename) {
        double[][] kernel = {
            {-1, 0, 1},
            {-1, 1, 1},
            {-1, 0, 1}
        };
        Picture pic = new Picture(filename);
        return applyConvolutionFilter(pic, kernel);
    }
    public static Picture sharpenFilter(String filename) {
        double[][] kernel = {
            {0, -1, 0},
            {-1, 5, -1},
            {0, -1, 0}
        };
        Picture pic = new Picture(filename);
        return applyConvolutionFilter(pic, kernel);
    }
    public static Picture oilPaintingFilter(String filename, int w) {
        Picture original = new Picture(filename);
        int width = original.width();
        int height = original.height();
        Picture output = new Picture(width, height);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Map<Color, Integer> colorCount = new HashMap<>();
                for (int i = -w; i <= w; i++) {
                    for (int j = -w; j <= w; j++) {
                        if (Math.abs(i) + Math.abs(j) < w) {
                            int xi = x + i;
                            int yj = y + j;
                            if (xi < 0 || xi >= width || yj < 0 || yj >= height) continue;
                            Color c = original.get(xi, yj);
                            colorCount.put(c, colorCount.getOrDefault(c, 0) + 1);
                        }
                    }
                }
                Color dominant = original.get(x, y);
                int maxCount = 0;
                for (Map.Entry<Color, Integer> entry : colorCount.entrySet()) {
                    if (entry.getValue() > maxCount) {
                        dominant = entry.getKey();
                        maxCount = entry.getValue();
                    }
                }
                output.set(x, y, dominant);
            }
        }
        return output;
    }
    public static Picture brighten(String filename, int delta) {
        Picture original = new Picture(filename);
        int width = original.width();
        int height = original.height();
        Picture output = new Picture(width, height);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Color c = original.get(x, y);
                int r = Math.min(255, Math.max(0, c.getRed() + delta));
                int g = Math.min(255, Math.max(0, c.getGreen() + delta));
                int b = Math.min(255, Math.max(0, c.getBlue() + delta));
                output.set(x, y, new Color(r, g, b));
            }
        }
        return output;
    }
    public static Picture sepiaFilter(String filename) {
        Picture original = new Picture(filename);
        int width = original.width();
        int height = original.height();
        Picture output = new Picture(width, height);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Color c = original.get(x, y);
                int r = c.getRed();
                int g = c.getGreen();
                int b = c.getBlue();
                int tr = (int)(0.393 * r + 0.769 * g + 0.189 * b);
                int tg = (int)(0.349 * r + 0.686 * g + 0.168 * b);
                int tb = (int)(0.272 * r + 0.534 * g + 0.131 * b);
                tr = Math.min(255, tr);
                tg = Math.min(255, tg);
                tb = Math.min(255, tb);
                output.set(x, y, new Color(tr, tg, tb));
            }
        }
        return output;
    }
    public static void main(String[] args) {
        printImageDimensions("input.jpg");

        Picture gray = convertToGrayscale("input.jpg");
        gray.show();
        gray.save("input-grayscale.jpg");

        Picture resized = resizePicture("input.jpg", 200, 200);
        resized.show();
        resized.save("input-resized.jpg");

        Picture[] gradients = createGradientPictures("input.jpg", 5);
        for (int i = 0; i < gradients.length; i++) {
            gradients[i].save("gradient_" + i + ".jpg");
        }

        Picture cropped = cropPicture("input.jpg", 10, 20, 20);
        cropped.show();
        cropped.save("input-cropped.jpg");

        Picture mirrored = mirrorY("input.jpg");
        mirrored.show();
        mirrored.save("input-mirror.jpg");

        Picture linear = linearFilter("input.jpg");
        linear.save("input-linear.jpg");
        Picture blur = blurFilter("input.jpg");
        blur.save("input-blur.jpg");
        Picture emboss = embossFilter("input.jpg");
        emboss.save("input-emboss.jpg");
        Picture sharpen = sharpenFilter("input.jpg");
        sharpen.save("input-sharpen.jpg");
        Picture oil = oilPaintingFilter("input.jpg", 4);
        oil.save("input-oil.jpg");

        Picture bright = brighten("input.jpg", 30);
        bright.save("input-bright.jpg");

        Picture sepia = sepiaFilter("input.jpg");
        sepia.save("input-sepia.jpg");
    }
}