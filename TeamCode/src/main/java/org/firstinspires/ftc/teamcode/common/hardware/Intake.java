package org.firstinspires.ftc.teamcode.common.hardware;

import androidx.annotation.NonNull;

import com.outoftheboxrobotics.photoncore.hardware.motor.PhotonDcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.mercurialftc.mercurialftc.scheduler.OpModeEX;
import org.mercurialftc.mercurialftc.scheduler.commands.Command;
import org.mercurialftc.mercurialftc.scheduler.commands.LambdaCommand;
import org.mercurialftc.mercurialftc.scheduler.subsystems.Subsystem;
import org.mercurialftc.mercurialftc.util.hardware.cachinghardwaredevice.CachingDcMotorEX;

import java.util.function.DoubleSupplier;

public class Intake extends Subsystem {
    DcMotorEx iMotor;
    public Intake(@NonNull OpModeEX opModeEX) {
        super(opModeEX);
    }

    @Override
    public void init() {
        iMotor = new CachingDcMotorEX(opModeEX.hardwareMap.get(DcMotorEx.class, "intake"));
    }

    @Override
    public void periodic() {

    }

    @Override
    public void defaultCommandExecute() {

    }

    @Override
    public void close() {

    }

    public Command setIntakePower(DoubleSupplier input){
        return new LambdaCommand().setRequirements(this).setInterruptible(true).setExecute(
                ()-> iMotor.setPower(input.getAsDouble())
        );
    }
}
