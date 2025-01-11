package com.example.demo.notification;

import com.example.demo.model.Appointment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService implements AppointmentObserver {

    @Autowired
    private JavaMailSender mailSender;

    @Override
    @Async
    public void update(Appointment appointment) {
        // ارسال ایمیل به دکتر
        sendDoctorEmail(appointment);
        // ارسال ایمیل به بیمار
        sendPatientEmail(appointment);
    }

    private void sendDoctorEmail(Appointment appointment) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("your-email@gmail.com");
            message.setTo(appointment.getDoctor().getEmail());
            message.setSubject("نوبت جدید پزشکی");
            message.setText(String.format(
                    "پزشک محترم %s,\n\n" +
                            "یک نوبت جدید در تاریخ %s با بیمار %s ثبت شده است.\n\n" +
                            "جزئیات نوبت:\n" +
                            "نام بیمار: %s\n" +
                            "تاریخ و ساعت: %s\n" +
                            "شماره تماس بیمار: %s\n\n" +
                            "با احترام,\n" +
                            "سیستم نوبت‌دهی",
                    appointment.getDoctor().getName(),
                    appointment.getAppointmentTime().toLocalDate(),
                    appointment.getPatient().getName(),
                    appointment.getPatient().getName(),
                    appointment.getAppointmentTime(),
                    appointment.getPatient().getPhoneNumber()
            ));

            mailSender.send(message);
        } catch (Exception e) {
            // لاگ کردن خطا
            System.err.println("خطا در ارسال ایمیل به دکتر: " + e.getMessage());
        }
    }

    private void sendPatientEmail(Appointment appointment) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("your-email@gmail.com");
            message.setTo(appointment.getPatient().getEmail());
            message.setSubject("تأیید نوبت پزشکی");
            message.setText(String.format(
                    "بیمار گرامی %s,\n\n" +
                            "نوبت شما با مشخصات زیر با موفقیت ثبت شد:\n\n" +
                            "نام پزشک: %s\n" +
                            "تخصص: %s\n" +
                            "تاریخ و ساعت: %s\n" +
                            "آدرس مطب: %s\n\n" +
                            "لطفاً ۱۵ دقیقه قبل از زمان نوبت در مطب حضور داشته باشید.\n\n" +
                            "با احترام,\n" +
                            "سیستم نوبت‌دهی",
                    appointment.getPatient().getName(),
                    appointment.getDoctor().getName(),
                    appointment.getDoctor().getSpecialization(),
                    appointment.getAppointmentTime(),
                    appointment.getDoctor().getAddress()
            ));

            mailSender.send(message);
        } catch (Exception e) {
            // لاگ کردن خطا
            System.err.println("خطا در ارسال ایمیل به بیمار: " + e.getMessage());
        }
    }
}