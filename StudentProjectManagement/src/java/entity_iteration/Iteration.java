/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity_iteration;

/**
 *
 * @author admin
 */
public class Iteration {
    private int id;
    private int subjectId;
    private String name, subjectName;
    private int evalWeight;
    private boolean onGoing;
    private String description;
    private boolean status;

    public Iteration(int id, String name, String subjectName, int evalWeight, boolean onGoing, String description, boolean status) {
        this.id = id;
        this.name = name;
        this.subjectName = subjectName;
        this.evalWeight = evalWeight;
        this.onGoing = onGoing;
        this.description = description;
        this.status = status;
    }

    public Iteration(int id, int subjectId, String name, int evalWeight, boolean onGoing, String description, boolean status) {
        this.id = id;
        this.subjectId = subjectId;
        this.name = name;
        this.evalWeight = evalWeight;
        this.onGoing = onGoing;
        this.description = description;
        this.status = status;
    }
    
    public Iteration() {        
    }


    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

   
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getEvalWeight() {
        return evalWeight;
    }

    public void setEvalWeight(int evalWeight) {
        this.evalWeight = evalWeight;
    }

    public boolean isOnGoing() {
        return onGoing;
    }

    public void setOnGoing(boolean onGoing) {
        this.onGoing = onGoing;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
    
}
