package org.firstinspires.ftc.teamcode.OPModes;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.sfdev.assembly.state.StateMachine;
import com.sfdev.assembly.state.StateMachineBuilder;
import com.sfdev.assembly.transition.TransitionCondition;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.common.CV.Location;
import org.firstinspires.ftc.teamcode.common.CV.blueDetector;
import org.firstinspires.ftc.teamcode.common.hardware.Deposit;
import org.firstinspires.ftc.teamcode.common.hardware.EndGame;
import org.firstinspires.ftc.teamcode.common.hardware.Intake;
import org.firstinspires.ftc.teamcode.common.hardware.MecanumDriveBase;
import org.mercurialftc.mercurialftc.scheduler.OpModeEX;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvWebcam;

@Autonomous(name = "Blue 50pt")
public class Blue50ptAuto extends OpModeEX {
    blueDetector pipeline;
    OpenCvWebcam webcam;
    String webcamName = "Webcam";
    private Deposit deposit;
    private MecanumDriveBase mecanumDriveBase;
    private EndGame endGame;
    private Intake intake;
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

    }

    @Override
    public void registerBindings() {

    }

    @Override
    public void init_loopEX() {

    }

    @Override
    public void startEX() {

    }

    @Override
    public void loopEX() {

    }

    @Override
    public void stopEX() {

    }
}
