package org.firstinspires.ftc.teamcode.common.CV;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

public class blueDetector extends OpenCvPipeline {
    public enum position {
        LEFT,
        RIGHT,
        MIDDLE
    }
    public position pos;
    Telemetry telemetry;
    Mat mat = new Mat();
    //define detection bounding boxes
    Rect LEFT_ROI = new Rect(
            new Point(425,50),
            new Point(700, 200)
    );
    Rect MID_ROI = new Rect(
            new Point(500, 50),
            new Point(400, 200)
    );
    Rect RIGHT_ROI = new Rect(
            new Point(450, 50),
            new Point(600, 200)
    );

    //define Minimum and Maximum blue values (what is blue and what is not)
    Scalar minHSV = new Scalar(180/2, 50,50);
    Scalar maxHSV = new Scalar(230/2, 240, 240);

    //initalise telemetry
    public blueDetector(Telemetry t){telemetry = t;}
    @Override
    public Mat processFrame(Mat input) {
        //Convert to HSV
        Imgproc.cvtColor(input, mat, Imgproc.COLOR_RGB2HSV);

        //Make all pixels that are blue turn white, everything else black
        Core.inRange(mat, minHSV, maxHSV, mat);

        //make matrixes of all the pixels inside of each of the rectangles
        Mat left = mat.submat(LEFT_ROI);
        Mat mid = mat.submat(MID_ROI);
        Mat right = mat.submat(RIGHT_ROI);

        //draw rects on the screen
        Imgproc.rectangle(mat, LEFT_ROI, new Scalar(255,0,0));
        //Imgproc.rectangle(mat, MID_ROI, new Scalar(255,0,0));
        //Imgproc.rectangle(mat, RIGHT_ROI, new Scalar(255,0,0));

        //find the average greyness
        double leftValue = Core.sumElems(left).val[0] / LEFT_ROI.area()/255;
        double rightValue = Core.sumElems(right).val[0] / RIGHT_ROI.area()/255;
        double midValue = Core.sumElems(mid).val[0] / MID_ROI.area()/255;

        if(leftValue<100){
            pos=position.LEFT;
        } else if (midValue<100) {
            pos=position.MIDDLE;
        } else if (rightValue<100) {
            pos=position.RIGHT;
        } else{
             pos = null;
        }

        return mat;
    }
}
