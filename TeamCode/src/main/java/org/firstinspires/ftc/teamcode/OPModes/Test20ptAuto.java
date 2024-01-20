package org.firstinspires.ftc.teamcode.OPModes;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.sfdev.assembly.state.StateMachine;
import com.sfdev.assembly.state.StateMachineBuilder;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.common.CV.Location;
import org.firstinspires.ftc.teamcode.common.CV.saturationDetector;
import org.firstinspires.ftc.teamcode.common.hardware.Deposit;
import org.firstinspires.ftc.teamcode.common.hardware.EndGame;
import org.firstinspires.ftc.teamcode.common.hardware.Intake;
import org.firstinspires.ftc.teamcode.common.hardware.MechanumDriveBase;
import org.mercurialftc.mercurialftc.scheduler.OpModeEX;
import org.mercurialftc.mercurialftc.silversurfer.geometry.Pose2D;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvWebcam;

@Autonomous
public class Test20ptAuto extends OpModeEX {
    private StateMachine stateMachine;
    enum States {
        SPIKEMARK,
        DEPOSITING,
        PARKING
    }

    private Deposit deposit;
    private MechanumDriveBase mecanumDriveBase;
    private EndGame endGame;
    private Intake intake;

    saturationDetector pipeline;
    OpenCvWebcam webcam;
    String webcamName = "Webcam";
    @Override
    public void registerSubsystems() {
        deposit= new Deposit(this);
        mecanumDriveBase = new MechanumDriveBase(this, new Pose2d());
        endGame = new EndGame(this);
        intake = new Intake(this);
    }

    @Override
    public void initEX() {
         stateMachine = new StateMachineBuilder()
                 .state(States.SPIKEMARK)
                 .onEnter()
                 .transitionTimed(0, States.DEPOSITING)
                 .state(States.DEPOSITING)
                 .onEnter(()->intake.setIntakePower(()->-0.5).queue())
                 .onExit(()->intake.stop().queue())
                 .transitionTimed(2)
                 .state(States.PARKING)
                 .onEnter(mecanumDriveBase.park().queue())
                 .build();

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

        pipeline = new saturationDetector(telemetry);
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

    }

    @Override
    public void registerBindings() {
    }

    @Override
    public void init_loopEX() {

    }

    @Override
    public void startEX() {
        telemetry.addData("position", saturationDetector.pos);
    }

    @Override
    public void loopEX() {

    }

    @Override
    public void stopEX() {
        deposit.stop();
        mecanumDriveBase.stop();
        intake.setIntakePower(()->0);
        endGame.manualWinchControl(()->0);
    }
}
