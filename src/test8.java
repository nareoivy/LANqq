import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
class test7 extends JPanel//此处类继承了JPanel类
{
    static final int WIDTH=300;
    static final int HEIGHT=150;
JFrame loginframe;
    public void add(Component c,GridBagConstraints constraints,int x,int y,int w,int h)
    {//此方法用来添加控件到容器中
        constraints.gridx=x;
        constraints.gridy=y;
        constraints.gridwidth=w;
        constraints.gridheight=h;
        add(c,constraints);
} 
    test7()
    {
        loginframe=new JFrame("信息管理系统"); //设置顶层容器
        loginframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//设置其顶层容器的关闭性
        GridBagLayout lay=new GridBagLayout();//创建网格组布局方式对象 
        setLayout(lay);                        
        loginframe.add(this, BorderLayout.WEST);
        loginframe.setSize(WIDTH,HEIGHT);
        Toolkit kit=Toolkit.getDefaultToolkit();//设置顶层容器框架为居中
        Dimension screenSize=kit.getScreenSize();
        int width=screenSize.width;
        int height=screenSize.height;
        int x=(width-WIDTH)/2;
        int y=(height-HEIGHT)/2;
        loginframe.setLocation(x,y);
        JButton ok=new JButton("确认");
        JButton cancel=new JButton("放弃");
        JLabel title=new JLabel("布局管理器测试窗口");
        JLabel name=new JLabel("用户名:");
        JLabel password=new JLabel("密 码:");
        final JTextField nameinput=new JTextField(18);
        final JTextField passwordinput=new JTextField(18);
        GridBagConstraints constraints=new GridBagConstraints();
        constraints.fill=GridBagConstraints.NONE;
        constraints.anchor=GridBagConstraints.EAST;

        constraints.weightx=5;
        constraints.weighty=4;
        add(title,constraints,0,0,5,1); //使用网格组布局添加控件                
        add(name,constraints,0,1,1,1);
        add(password,constraints,0,2,1,1);
        add(nameinput,constraints,1,1,4,1);
        add(passwordinput,constraints,1,2,4,1);
        add(ok,constraints,1,3,1,1);
        add(cancel,constraints,3,3,1,1);
        loginframe.setResizable(false);
        loginframe.setVisible(true);  
    }
}
public class test8
{
    public static void main(String[] args)
    {
    	test7 log=new test7();
}
 }