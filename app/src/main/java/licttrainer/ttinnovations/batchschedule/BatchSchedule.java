package licttrainer.ttinnovations.batchschedule;
import java.io.Serializable;
import java.util.Date;

public class BatchSchedule implements Serializable
{
    private static final long serialVersionUID = 1L;
    private Long id;
    private Date date,till;
    private String university;
    private String Status,trainer,batchcode,comment;

    public Date getTill() {
        return till;
    }

    public void setTill(Date till) {
        this.till = till;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }

    public String getTrainer() {
        return trainer;
    }

    public void setTrainer(String trainer) {
        this.trainer = trainer;
    }

    public String getBatchcode() {
        return batchcode;
    }

    public void setBatchcode(String batchcode) {
        this.batchcode = batchcode;
    }



    @Override
    public String toString() {
        return "BatchSchedule{" + "id=" + id + ", date=" + date + ", university=" + university + ", Status=" + Status + ", trainer=" + trainer + ", batchcode=" + batchcode + '}';
    }
}

