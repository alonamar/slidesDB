package dbApp.view;

public class Testcls<E> {
    private E one;

    public Testcls(E one) {
        this.one = one;
    }

    public void check(){
        if(one.getClass() == String.class){
            System.out.println("string");
        }else if(one.getClass() == Integer.class){
            System.out.println("int");
        }else{
            System.out.println("no");
        }
    }
}
