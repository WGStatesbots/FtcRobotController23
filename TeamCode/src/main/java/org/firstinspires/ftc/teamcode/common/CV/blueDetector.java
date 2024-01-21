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
    static public Location pos;
    Telemetry telemetry;
    Mat mat = new Mat();
    //define detection bounding boxes
    Rect LEFT_ROI = new Rect(
            new Point(0,0),
            new Point(400, 720)
    );
    Rect MID_ROI = new Rect(
            new Point(400, 0),
            new Point(800, 720)
    );
    Rect RIGHT_ROI = new Rect(
            new Point(800, 0),
            new Point(1200, 720)
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

        if(leftValue<400){
            pos=Location.LEFT;
        } else if (midValue<400) {
            pos=Location.CENTER;
        } else if (rightValue<400) {
            pos=Location.RIGHT;
        } else{
             pos = null;
        }
        telemetry.addData("left:", leftValue);
        telemetry.addData("right:", rightValue);
        telemetry.addData("middle:", midValue);
        return mat;
    }
}
