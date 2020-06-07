package controllers;

import models.Member;
import models.Trainer;
import play.Logger;
import play.mvc.Controller;

public class Accounts extends Controller {
    public static void signup() {
        render("signup.html");
    }

    public static void login() {
        render("login.html");
    }

    public static void register(String name, int age, String email, String password, String address, String gender, double height, double weight, double bmi) {
        Logger.info("Registering new user " + email);
        Member member = new Member(name, age, email, password, gender, address, height,weight );
        member.save();
        redirect("/");
    }



    public static void authenticate(String email, String password) {
        Logger.info("Attempting to authenticate with " + email + ":" + password);

        Member member = Member.findByEmail(email);
        if ((member != null) && (member.checkPassword(password) == true)) {
            Logger.info("Authentication successful");
            session.put("logged_in_Memberid", member.id);
            redirect("/dashboard");
        } else {

            Trainer trainer = Trainer.findByEmail(email);
            if ((trainer != null) && (trainer.checkPassword(password) == true)) {
                Logger.info("Authentication successful");
                session.put("logged_in_Trainerid", trainer.id);
                redirect("/trainerDashboard");
            } else {
                Logger.info("Authentication failed");
                redirect("/login");
            }
        }

    }

    public static void logout() {
        session.clear();
        redirect("/");
    }

    public static Member getLoggedInMember() {
        Member member = null;
        if (session.contains("logged_in_Memberid")) {
            String memberId = session.get("logged_in_Memberid");
            member = Member.findById(Long.parseLong(memberId));
        } else {
            login();
        }
        return member;
    }

    public static Trainer getLoggedInTrainer() {
        Trainer trainer = null;
        if (session.contains("logged_in_Trainerid")) {
            String trainerId = session.get("logged_in_Trainerid");
            trainer = Trainer.findById(Long.parseLong(trainerId));
        } else {
            login();
        }
        return trainer;
    }
}