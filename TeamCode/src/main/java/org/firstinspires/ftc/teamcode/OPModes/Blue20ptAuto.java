package org.firstinspires.ftc.teamcode.OPModes;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.common.CV.blueDetector;
import org.firstinspires.ftc.teamcode.common.RR.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.teamcode.common.hardware.Deposit;
import org.firstinspires.ftc.teamcode.common.hardware.EndGame;
import org.firstinspires.ftc.teamcode.common.hardware.Intake;
import org.firstinspires.ftc.teamcode.common.hardware.MecanumDriveBase;
import org.mercurialftc.mercurialftc.scheduler.OpModeEX;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvWebcam;

@Autonomous(name = "Blue 20pt")
public class Blue20ptAuto extends OpModeEX {
    blueDetector pipeline;
    OpenCvWebcam webcam;
    String webcamName = "Webcam";
    private Deposit deposit;
    private MecanumDriveBase mecanumDriveBase;
    private EndGame endGame;
    private Intake intake;

    TrajectorySequence leftTraj, rightTraj, midTraj;
    @Override
    public void registerSubsystems() {
        deposit= new Deposit(this);
        mecanumDriveBase = new MecanumDriveBase(this, new Pose2d());
        endGame = new EndGame(this);
        intake = new Intake(this);
    }

    @Override
    public void initEX() {
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
                webcam.startStreaming(1280, 720, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode) {
            }
        });
        FtcDashboard.getInstance().startCameraStream(webcam, 0);

        leftTraj = mecanumDriveBase.drive.trajectorySequenceBuilder(new Pose2d(15, 60, Math.toRadians(90)))
                .lineToLinearHeading(new Pose2d(25,34, Math.toRadians(-180)))
                .addTemporalMarker(()->deposit.manualDepositControl(()->1).queue())
                .addTemporalMarker(1, ()->deposit.manualDepositControl(()->0).queue())
                .build();
    }

    @Override
    public void registerBindings() {

    }

    @Override
    public void init_loopEX() {

    }

    @Override
    public void startEX() {
        mecanumDriveBase.drive.followTrajectorySequence(leftTraj);
    }

    @Override
    public void loopEX() {
        mecanumDriveBase.drive.update();
    }

    @Override
    public void stopEX() {

    }
}
