package org.firstinspires.ftc.teamcode.common.CV;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvWebcam;
@Autonomous(name = "cvTest")
public class cvTest extends LinearOpMode {
    OpenCvWebcam webcam;

    @Override
    public void runOpMode() throws InterruptedException {
        blueDetector pipeline;
        OpenCvWebcam webcam;
        String webcamName = "Webcam";

        //add the positions EVERY SINGLE MECHANISM SHOULD BE IN
        //DO robot.resetEncoders() in here as well

        int cameraMonitorViewId = hardwareMap.appContext
                .getResources().getIdentifier(
                        "cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName()
                );

        webcam = OpenCvCameraFactory
                .getInstance().createWebcam(
                        hardwareMap.get(WebcamName.class, webcamName),
                        cameraMonitorViewId);

        pipeline = new blueDetector(telemetry);
        webcam.setPipeline(pipeline);

        webcam.setMillisecondsPermissionTimeout(5000); // Timeout for obtaining permission is configurable. Set before opening.

        webcam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {

            @Override
            public void onOpened() {
                webcam.startStreaming(1280, 720, OpenCvCameraRotation.UPSIDE_DOWN);
            }

            @Override
            public void onError(int errorCode) {
            }
        });
        FtcDashboard.getInstance().startCameraStream(webcam, 0);
        while(opModeInInit()){
            telemetry.addData("position", blueDetector.pos);
            telemetry.addData("vals", pipeline.leftValue);
            telemetry.addData("vals", pipeline.rightValue);
            telemetry.addData("vals", pipeline.midValue);
            telemetry.update();
        }
        waitForStart();

        while(opModeIsActive()){}
    }
}

