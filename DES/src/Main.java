public class Main {
    public static void main(String[] args) {
        String msg = "minamina"; //Should be 8 chars because It's a block cypher Algorithm
        DES des = new DES();
        String cypher = des.encryptMsg(msg);
        des.decryptMsg(cypher);
    }
}

