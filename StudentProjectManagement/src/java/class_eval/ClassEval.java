/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package class_eval;

/**
 *
 * @author HaiLong
 */
//bảng này lấy ra roll number, fullname, team = teamId, OG = ongoing_eval, total = final, iter 1,2,3,4 = total_grade
public class ClassEval {

    private int teamId;
    String fullname, role;
    int milestoneId;
    float iter1,iter2,iter3,iter4,iter5, onGoing, total, totalGrad;

   

   

    public int getMilestoneId() {
        return milestoneId;
    }

    public void setMilestoneId(int milestoneId) {
        this.milestoneId = milestoneId;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public ClassEval() {
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public float getIter1() {
        return iter1;
    }

    public void setIter1(float iter1) {
        this.iter1 = iter1;
    }

    public float getIter2() {
        return iter2;
    }

    public void setIter2(float iter2) {
        this.iter2 = iter2;
    }

    public float getIter3() {
        return iter3;
    }

    public void setIter3(float iter3) {
        this.iter3 = iter3;
    }

    public float getIter4() {
        return iter4;
    }

    public void setIter4(float iter4) {
        this.iter4 = iter4;
    }

    public float getIter5() {
        return iter5;
    }

    public void setIter5(float iter5) {
        this.iter5 = iter5;
    }

    public float getOnGoing() {
        return (iter1 * 5 + iter2 *15 + iter3 *15 + iter4 *15 + iter5 *5)/100;
    }

    public void setOnGoing(float onGoing) {
        this.onGoing = onGoing;
    }

    public float getTotal() {
        return total;
    }
    public float getFinal() {return total;}

    public void setTotal(float total) {
        this.total = total;
    }

    public float getTotalGrad() {
        return totalGrad;
    }

    public void setTotalGrad(float totalGrad) {
        this.totalGrad = totalGrad;
    }

    

}
