package org.firstinspires.ftc.teamcode.OPModes;

import org.firstinspires.ftc.teamcode.common.hardware.Deposit;
import org.firstinspires.ftc.teamcode.common.hardware.EndGame;
import org.firstinspires.ftc.teamcode.common.hardware.MechanumDriveBase;
import org.mercurialftc.mercurialftc.scheduler.OpModeEX;
import org.mercurialftc.mercurialftc.scheduler.bindings.Binding;
import org.mercurialftc.mercurialftc.scheduler.commands.ParallelCommandGroup;
import org.mercurialftc.mercurialftc.scheduler.commands.SelectionCommandGroup;
import org.mercurialftc.mercurialftc.scheduler.commands.SequentialCommandGroup;
import org.mercurialftc.mercurialftc.silversurfer.geometry.Pose2D;

public class TestOPEx extends OpModeEX {
    private Deposit deposit;
    private MechanumDriveBase mecanumDriveBase;
    private EndGame endGame;
    @Override
    public void registerSubsystems() {
        deposit= new Deposit(this);
        mecanumDriveBase = new MechanumDriveBase(this, new Pose2D());
        endGame = new EndGame(this);
    }

    @Override
    public void initEX() {
    }

    @Override
    public void registerBindings() {
        gamepadEX2().a().debounce(Binding.DebouncingType.LEADING_EDGE, 0.2).onTrue(new ParallelCommandGroup().addCommands(deposit.stop()).addCommands(mecanumDriveBase.stop()));

        gamepadEX2().leftY().buildBinding().lessThan(-0.01).greaterThan(0.01).bind()
                .whileTrue(deposit.manualControl(gamepadEX2().leftY()));

        gamepadEX2().right_trigger().buildBinding().lessThan(-0.01).greaterThan(0.01).bind()
                .whileTrue(deposit.manualDepositControl(gamepadEX2().right_trigger()));

        gamepadEX1().leftX().buildBinding().lessThan(-0.01).greaterThan(0.01).bind()
                .or(gamepadEX1().leftY().buildBinding().lessThan(-0.01).greaterThan(0.01).bind() )
                .or(gamepadEX1().rightX().buildBinding().lessThan(-0.01).greaterThan(0.01).bind())
                .whileTrue(mecanumDriveBase.manualControl(gamepadEX1().leftX(),gamepadEX1().leftY(),gamepadEX1().rightX()));

        gamepadEX2().left_bumper().debounce(Binding.DebouncingType.LEADING_EDGE, 0.2).onTrue(endGame.readyHook());
        gamepadEX2().right_bumper().debounce(Binding.DebouncingType.LEADING_EDGE, 0.2).onTrue(new SequentialCommandGroup()
                .addCommands(endGame.lowerHook())
                .addCommands(endGame.raiseRobot()));
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

    }
}
