

public class Processes implements Cloneable {
    
    String names;
    int dur;
    int inter;        
    int interDur;
    int priority;
    int lastRun;
    
    public Object clone() throws CloneNotSupportedException{
        return super.clone();
    }
  
    

    public Processes(String names, int dur, int inter, int interDur, int priority, int lastRun) {
        this.names = names;
        this.dur = dur;
        this.inter = inter;
        this.interDur = interDur;
        this.priority = priority;
        this.lastRun = lastRun;
    }
    
    public String getNames() {
        return names;
    }
    public void setNames(String names) {
        this.names = names;
    }
    public int getDur() {
        return dur;
    }
    public void setDur(int dur) {
        this.dur = dur;
    }
    public int getInter() {
        return inter;
    }
    public void setInter(int inter) {
        this.inter = inter;
    }
    public int getInterDur() {
        return interDur;
    }
    public void setInterDur(int interDur) {
        this.interDur = interDur;
    }
    public int getLastRun() {
        return lastRun;
    }
    public void setLastRun(int lastRun) {
        this.lastRun = lastRun;
    }
    public int getPriority() {
        return priority;
    }
    public void setPriority(int priority) {
        this.priority = priority;
    }


    
        
}
