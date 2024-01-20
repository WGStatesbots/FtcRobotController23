package org.firstinspires.ftc.teamcode.OPModes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.common.hardware.Deposit;
import org.firstinspires.ftc.teamcode.common.hardware.EndGame;
import org.firstinspires.ftc.teamcode.common.hardware.Intake;
import org.firstinspires.ftc.teamcode.common.hardware.MechanumDriveBase;
import org.mercurialftc.mercurialftc.scheduler.OpModeEX;
import org.mercurialftc.mercurialftc.scheduler.bindings.Binding;
import org.mercurialftc.mercurialftc.scheduler.commands.ParallelCommandGroup;
import org.mercurialftc.mercurialftc.scheduler.commands.SequentialCommandGroup;
import org.mercurialftc.mercurialftc.silversurfer.geometry.Pose2D;

import java.util.function.BooleanSupplier;

@SuppressWarnings("unused")
@TeleOp(name = "OPModeEx")
public class TestOPEx extends OpModeEX {
    private Deposit deposit;
    private MechanumDriveBase mecanumDriveBase;
    private EndGame endGame;
    private Intake intake;
    @Override
    public void registerSubsystems() {
        deposit= new Deposit(this);
        mecanumDriveBase = new MechanumDriveBase(this, new Pose2D());
        endGame = new EndGame(this);
        intake = new Intake(this);
    }

    @Override
    public void initEX() {
    }

    @Override
    public void registerBindings() {
        //Stops robot
        gamepadEX2().a().debounce(Binding.DebouncingType.LEADING_EDGE, 0.2).onTrue(new ParallelCommandGroup()
                .addCommands(deposit.stop())
                .addCommands(mecanumDriveBase.stop())
                .addCommands(endGame.manualWinchControl(()->0)));

        //Control the deposit (slides) manually
        gamepadEX2().leftY().buildBinding().lessThan(-0.01).greaterThan(0.01).bind()
                .whileTrue(deposit.manualControl(gamepadEX2().leftY()));
        //Control the servo
        gamepadEX2().right_trigger().buildBinding().lessThan(-0.01).greaterThan(0.01).bind()
                .whileTrue(deposit.manualDepositControl(gamepadEX2().right_trigger()));
        //Raise the hook
        gamepadEX2().left_bumper().debounce(Binding.DebouncingType.LEADING_EDGE, 0.2).onTrue(endGame.readyHook());
        //Lower hook, then try to raise the robot until power gets too high (may need tuning, 5a just sounded right as stall current is 8a iirc)
        gamepadEX2().right_bumper().debounce(Binding.DebouncingType.LEADING_EDGE, 0.2).onTrue(new SequentialCommandGroup()
                .addCommands(endGame.lowerHook())
                .addCommands(endGame.raiseRobot()))
                .onFalse(endGame.drop());
        gamepadEX2().start().debounce(Binding.DebouncingType.LEADING_EDGE, 0.2).onTrue(endGame.launchDrone());

        //Move with small dead zones (prevent whine)
        gamepadEX1().leftX().buildBinding().lessThan(-0.01).greaterThan(0.01).bind()
                .or(gamepadEX1().leftY().buildBinding().lessThan(-0.01).greaterThan(0.01).bind())
                .or(gamepadEX1().rightX().buildBinding().lessThan(-0.01).greaterThan(0.01).bind())
                .whileTrue(mecanumDriveBase.manualControl(gamepadEX1().leftX(),gamepadEX1().leftY(),gamepadEX1().rightX()));
        //Intake
        gamepadEX1().right_bumper().debounce(Binding.DebouncingType.BOTH, 0.2).onTrue(intake.setIntakePower(()->0.5)).onFalse(intake.setIntakePower(()->0));
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
        deposit.stop();
        mecanumDriveBase.stop();
        intake.setIntakePower(()->0);
        endGame.manualWinchControl(()->0);
    }
}
