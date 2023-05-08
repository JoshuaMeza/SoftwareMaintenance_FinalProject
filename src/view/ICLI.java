package src.view;

public interface ICLI {
    // Basic funcs
    public void update(String[] list);
    public void print(String msg);
    public String read(String desc);
    public void clear();
    // Logs msg    
    public void successMsg(String msg);
    public void warningMsg(String msg);
    public void dangerMsg(String msg);
}
