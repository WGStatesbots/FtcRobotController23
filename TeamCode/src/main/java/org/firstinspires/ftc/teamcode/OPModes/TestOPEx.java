package org.firstinspires.ftc.teamcode.OPModes;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.common.hardware.Deposit;
import org.firstinspires.ftc.teamcode.common.hardware.EndGame;
import org.firstinspires.ftc.teamcode.common.hardware.Intake;
import org.firstinspires.ftc.teamcode.common.hardware.MecanumDriveBase;
import org.mercurialftc.mercurialftc.scheduler.OpModeEX;
import org.mercurialftc.mercurialftc.scheduler.bindings.Binding;
import org.mercurialftc.mercurialftc.scheduler.commands.ParallelCommandGroup;

@SuppressWarnings("unused")
@TeleOp(name = "OPModeEx")
public class TestOPEx extends OpModeEX {
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
    }

    @Override
    public void registerBindings() {
        //Stops robot
        gamepadEX2().a().debounce(Binding.DebouncingType.LEADING_EDGE, 0.05).onTrue(new ParallelCommandGroup()
                .addCommands(deposit.stop())
                .addCommands(mecanumDriveBase.stop())
                .addCommands(endGame.manualWinchControl(()->0)));

        //Control the deposit (slides) manually
        gamepadEX2().leftY().buildBinding().lessThan(-0.01).greaterThan(0.01).bind()
                .whileTrue(deposit.manualControlCommand(gamepadEX2().leftY().invert())).onFalse(deposit.stop());
        //Control the servo
        gamepadEX2().right_trigger().buildBinding().greaterThan(0.01).bind()
                .whileTrue(deposit.manualDepositControlCommand(gamepadEX2().right_trigger())).onFalse(deposit.stop());
        //Raise the hook
        gamepadEX2().left_bumper().debounce(Binding.DebouncingType.LEADING_EDGE, 0.05).onTrue(endGame.readyHook());
        //Lower hook, then try to raise the robot until power gets too high (may need tuning, 5a just sounded right as stall current is 8a iirc)
        gamepadEX2().right_bumper().debounce(Binding.DebouncingType.LEADING_EDGE, 0.05).onTrue(endGame.lowerHook()).onFalse(endGame.drop());
        gamepadEX2().dpad_up().debounce(Binding.DebouncingType.LEADING_EDGE, 0.05).onTrue(endGame.launchDrone());
        gamepadEX2().rightY().buildBinding().greaterThan(0.5).lessThan(-0.5).bind().whileTrue(endGame.manualWinchControl(gamepadEX2().rightY()));

        //Move with small dead zones (prevent whine)
        /*gamepadEX1().leftX().buildBinding().lessThan(-0.01).greaterThan(0.01).bind()
                .or(gamepadEX1().leftY().buildBinding().lessThan(-0.01).greaterThan(0.01).bind())
                .or(gamepadEX1().rightX().buildBinding().lessThan(-0.01).greaterThan(0.01).bind())
                .whileTrue(mecanumDriveBase.manualControl(gamepadEX1().leftX(),gamepadEX1().leftY(),gamepadEX1().rightX()));*/
        //if you get an error on the line above, comment it out, uncomment line 82, and comment line 25 of MechanumDriveBase out
        //Intake
        gamepadEX1().a().debounce(Binding.DebouncingType.LEADING_EDGE, 0.05).onTrue(intake.setIntakePower(()->0.5)).onFalse(intake.setIntakePower(()->0));
        gamepadEX1().b().debounce(Binding.DebouncingType.LEADING_EDGE, 0.05).onTrue(intake.setIntakePower(()->-0.5)).onFalse(intake.setIntakePower((()->0)));

        gamepadEX1().right_trigger().buildBinding().greaterThan(0.5).bind().onTrue(intake.clawClose()).onFalse(intake.clawOpen());
    }

    @Override
    public void init_loopEX() {

    }

    @Override
    public void startEX() {

    }

    @Override
    public void loopEX() {
        mecanumDriveBase.drive.setDrivePower(new Pose2d(Math.pow(-gamepad1.left_stick_y,3)/2, Math.pow(-gamepad1.left_stick_x,3)/2, Math.pow(-gamepad1.right_stick_x, 3)/2));
    }

    @Override
    public void stopEX() {
        deposit.stop();
        mecanumDriveBase.stop();
        intake.setIntakePower(()->0);
        endGame.manualWinchControl(()->0);
    }
}
