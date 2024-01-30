package org.firstinspires.ftc.teamcode.OPModes;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;

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

@Autonomous
public class BackstageBlueAuto extends OpModeEX {
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
                webcam.startStreaming(1280, 720, OpenCvCameraRotation.UPSIDE_DOWN);
            }

            @Override
            public void onError(int errorCode) {
            }
        });
        FtcDashboard.getInstance().startCameraStream(webcam, 0);

        leftTraj = mecanumDriveBase.drive.trajectorySequenceBuilder(new Pose2d(12.00, 63.00, Math.toRadians(90.00)))
                .addDisplacementMarker(0.4,0,()->{
                    intake.setIntakePower(()->-1).queue();
                    deposit.manualControl(()->-1);
                })
                .addDisplacementMarker(0.5,0,()->{
                    intake.setIntakePower(()->0).queue();
                    deposit.setZeroMode(DcMotor.ZeroPowerBehavior.BRAKE);
                    deposit.manualControl(()->0);
                })
                .addDisplacementMarker(0.6,0,()->{
                    deposit.manualControl(()->0);
                    deposit.manualDepositControl(()->1);
                })
                .addDisplacementMarker(.85,0, ()->{
                    deposit.setZeroMode(DcMotor.ZeroPowerBehavior.FLOAT);
                    deposit.manualDepositControl(()->0);
                })
                .setReversed(true)
                .splineTo(new Vector2d(35.00, 34.00), Math.toRadians(180))
                .setReversed(false)
                .splineTo(new Vector2d(48.00, 42.00), Math.toRadians(0))
                .setReversed(true)
                .waitSeconds(2)
                .splineToConstantHeading(new Vector2d(42, 61), Math.toRadians(0.00))
                .setReversed(true)
                .build();

        midTraj = mecanumDriveBase.drive.trajectorySequenceBuilder(new Pose2d(12, 63, Math.toRadians(90)))
                .addDisplacementMarker(0.1,0,()->{
                            intake.setIntakePower(()->-1).queue();
                })
                .addDisplacementMarker(0.4,0,()->{
                            intake.setIntakePower(()->0).queue();
                            deposit.setZeroMode(DcMotor.ZeroPowerBehavior.BRAKE);
                })
                .addDisplacementMarker(0.6,0, ()->{
                            deposit.manualControl(()->-1);
                })
                .addDisplacementMarker(0.69,0,()->{
                            deposit.manualControl(()->0);
                            deposit.manualDepositControl(()->1);
                })
                .addDisplacementMarker(0.75,0, ()->{
                            deposit.setZeroMode(DcMotor.ZeroPowerBehavior.FLOAT);
                            deposit.manualDepositControl(()->0);
                })
                .setReversed(true)
                .splineTo(new Vector2d(12.00, 35.00), Math.toRadians(-90))
                .setReversed(false)
                .splineTo(new Vector2d(48.00, 36.00), Math.toRadians(0))
                .setReversed(true)
                .waitSeconds(3)
                .splineToConstantHeading(new Vector2d(48.00, 61.00), Math.toRadians(0.00))
                .setReversed(false)
                .build();

        rightTraj = mecanumDriveBase.drive.trajectorySequenceBuilder(new Pose2d(12.00, 63.00, Math.toRadians(90.00)))
                .addDisplacementMarker(0.25,0,()->{
                    intake.setIntakePower(()->-1).queue();
                })
                .addDisplacementMarker(0.4,0,()->{
                    intake.setIntakePower(()->0).queue();
                    deposit.setZeroMode(DcMotor.ZeroPowerBehavior.BRAKE);
                })
                .addDisplacementMarker(0.55,0, ()->{
                    deposit.manualControl(()->-1);
                })
                .addDisplacementMarker(0.63,0,()->{
                    deposit.manualControl(()->0);
                    deposit.manualDepositControl(()->1);
                })
                .addDisplacementMarker(0.75,0, ()->{
                    deposit.setZeroMode(DcMotor.ZeroPowerBehavior.FLOAT);
                    deposit.manualDepositControl(()->0);
                })
                .setReversed(true)
                .splineTo(new Vector2d(7.00, 31.00), Math.toRadians(180.00))
                .setReversed(false)
                .splineTo(new Vector2d(46.00, 29.00), Math.toRadians(0.00))
                .waitSeconds(3)
                .setReversed(true)
                .splineToLinearHeading(new Pose2d(48.00, 61.00,0), Math.toRadians(0.00))
                .setReversed(false)
                .build();

        mecanumDriveBase.drive.setPoseEstimate(leftTraj.start());
    }

    @Override
    public void registerBindings() {

    }

    @Override
    public void init_loopEX() {

    }

    @Override
    public void startEX() {
        switch (blueDetector.pos){
            case LEFT:
                mecanumDriveBase.drive.followTrajectorySequenceAsync(leftTraj);
                break;
            case CENTER:
                mecanumDriveBase.drive.followTrajectorySequenceAsync(midTraj);
                break;
            case RIGHT:
                mecanumDriveBase.drive.followTrajectorySequenceAsync(rightTraj);
                break;
        }
    }

    @Override
    public void loopEX() {
        mecanumDriveBase.drive.update();
    }

    @Override
    public void stopEX() {

    }
}
