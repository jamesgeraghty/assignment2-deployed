package controllers;

import models.Member;
import models.Assessment;
import play.mvc.Controller;
import play.Logger;

public class MemberCtrl extends Controller
{
    public static void index(Long id)
{
    Member member = Member.findById(id);
    Logger.info ("Member id = " + id);
    render("member.html",member);

}

    public static void deleteassessment (Long id, Long assessmentid)
    {
        Member member = Member.findById(id);
        Assessment assessment = Assessment.findById(assessmentid);
        Logger.info("Removing" + assessment.weight + assessment.chest + assessment.thigh
                + assessment.upperArm + assessment.waist + assessment.hips);
        member.assessments.remove(assessment);
        member.save();
        assessment.delete();

        render("member.html", member);
    }
    public static void addAssessments(Long id, double weight, double chest, double thigh, double upperArm, double waist, double hips, String comment) {
        Assessment assessment = new Assessment(weight, chest, thigh,upperArm,waist,hips,comment);
        Member member = Member.findById(id);
        member.assessments.add(assessment);
        member.save();
        redirect ("/member/" + id);
    }


}