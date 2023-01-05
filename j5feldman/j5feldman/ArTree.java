/*
 * ArTree.java
 *
 * Created on 15 ƒекабрь 2004 г., 9:29
 */

package j5feldman;
import java.sql.*;
import java.lang.*;
import java.util.*;
import javax.servlet.http.*;
/**
 *
 * @author  Jacob Feldman / ‘ельдман яков јдольфович
 */
public class ArTree {
    PNode obj;
    ArNode rootNode;
    /** Creates a new instance of ArTree */
    public ArTree(XPump pp,String typename,int qid)throws Exception {
        this.qid = qid;
        this.pp = pp;
        rootNode = new ArNode(typename);
        nodes.add(rootNode);
        obj = pp.getNode(FTree.ObjectTree,qid);
    }
    String startTableId="";
    XPump pp;
    List<ArNode> nodes=new ArrayList<ArNode>();
    int NUM=0;
    String qtxt="";
    int qid;
    enum NodeType{KEY,TABLE,TYPE,FIELD}

    class ArNode{
        NodeType ntype;
        String icon() throws Exception {
            String name =
                    isType()?pp.icon(typename):
                        isTable()?pp.icon(tabname):
                        isKey()?"key":"object";
            String s = "<img alt='"+name+"' "+
                    " border=0 src="+pp.iconpath()+name+".gif>";
            return s;
        }
        ArNode(String typename){
            this.ntype = NodeType.TYPE;
            num = NUM++;
            this.typename = typename;
            this.tabname = typename;
            this.colname = "id";
        }
        ArNode(String typename,String tabname){
            this.ntype = NodeType.TABLE;
            num = NUM++;
            this.typename = typename;
            this.tabname = tabname;
            this.colname = "id";
        }
        ArNode(String typename,String tabname,String colname,
                String reftable,String fk_name){
            num = NUM++;
            this.typename = typename;
            this.tabname = tabname;
            this.colname = colname;
            this.reftable = reftable;
            this.fk_name = fk_name;
            this.ntype = 
                    reftable!=null&&!fk_name.equals("fk_object_type")
                    ?NodeType.KEY
                    :NodeType.FIELD;            
        }
        //////////////////////////////////////////////////////////////
        String longName(){
            return isType()?typename:
                isTable()?typename+":"+tabname:
                isKey()?fk_name:
                    tabname+"::"+colname;
        }        
        String name(){
            if(isType())return typename;
            else if(isTable())return tabname;
            else if(isField())return colname;
            else return fk_name;
        }
        ////////////////////////////////////////////////////////////////
        String fk_name;
        int parentNum;
        int pos;
        int ord;
        String col;
        String typename;
        String tabname;
        String colname;
        String reftable;
        boolean collapsed=true;
        int num;
        int depth;
//        boolean hasChildren=true;
        boolean inReport = false;
        boolean isRootTable(){return parentNum==0 && isTypeTable();}
        boolean isKey(){return ntype==NodeType.KEY;}
        boolean isField(){return ntype==NodeType.FIELD;}
        boolean isType(){return ntype==NodeType.TYPE;}
        boolean isTable(){return ntype==NodeType.TABLE;}
        boolean isTypeTable(){return isTable() && typename.equals(tabname);}
        String tab(){
            return " t"+num+tabname;
        }
        String tab2(){
            return tabname+tab();
        }
    }
    public void expand(int n)throws Exception{
        ArNode parent = nodes.get(n);
        List<ArNode> children = getChildren(parent);
        for(ArNode c:children){
            c.depth = parent.depth+1;
//            c.num = NUM++;
        }
        nodes.addAll(n+1,children);
        parent.collapsed=false;
    }
    public void collapse(int n)throws Exception{
        ArNode parent = nodes.get(n);
        if(parent.collapsed) return;
        while(n<nodes.size()-1 && nodes.get(n).depth < nodes.get(n+1).depth){
            nodes.remove(n+1);
        }
        parent.collapsed=true;
    }
    
    boolean hasChildren(ArNode node){
        return !node.isField();
    }
    
    List<ArNode> getChildren(ArNode node)throws Exception{
        if(node.isKey()){
            List<ArNode> list = new ArrayList<ArNode>();
            if(!node.reftable.equals("type"))
                list.add(typeChild(node));
            return list;
        }else if(node.isType()){return getTables(node);
        }else if(node.isTable()){return fkChildren(node);}
        throw new Exception("field has no children!");
    }
    List<ArNode> getTables(ArNode type)throws Exception{
        List<ArNode> list = new ArrayList();
        List<P2Node> list2 = P2Node.pathUp(pp,type.typename);
        for(P2Node node:list2){
            if(!node.isAbstr()){
                ArNode table = new ArNode(type.typename,node.code);
                table.depth = type.depth+1;
                table.parentNum = type.num;
                list.add(0,table);
            }
        }
        return list;
    }    
    ArNode typeChild(ArNode parentNode){
        ArNode node = new ArNode(parentNode.reftable);
        node.depth = parentNode.depth+1;
        node.parentNum = parentNode.num;
        return node;
    }

    List<ArNode> fkChildren(ArNode tableNode)throws Exception{
        List<ArNode> ar = new ArrayList<ArNode>();
        Table t = new Table(pp,tableNode.tabname,false,false);
//        List<Field> fields = Table.getFields(pp,tableNode.tabname,false);
        for(ITabCol fc:t.fields){
            Field f = (Field)fc;
            if(!f.tabname.equals("object")&&f.name.equals("id"))continue;
            ArNode node = new ArNode
                    (tableNode.typename,f.tabname,f.name,f.reftab,f.fk_name);
            node.depth = tableNode.depth+1;
            node.parentNum = tableNode.num;
            ar.add(node);
        }
        return ar;
    }
    void normal2()throws Exception{
        for(ArNode node:nodes){
            if(node.isField()) node.inReport=true;
            ArNode nx = node;
            while(nx.num>0){
                ArNode nn = nodes.get(num2n(nx.parentNum));
                nn.inReport=true;
                nx = nn;
            }
        }
        for(int k=0;k<nodes.size();k++){
            ArNode node=nodes.get(k);
            if(!node.inReport){nodes.remove(k);k--;}
        }
        
        qtxt = qtxt();///////////////
    }
    void normal()throws Exception{
        for(int k=0;k<nodes.size();k++){
            ArNode node=nodes.get(k);
            if(node.isField() && node.pos==0){ nodes.remove(k);k--;}
        }
        normal2();
    }
    
    void setFieldsTab(int num,int pos,int ord,String col)throws Exception{
        if(pos<=0)return;
        ArNode node = nodes.get(num2n(num));
        node.pos = pos; node.ord = ord; node.col = col; //node.wh = wh; node.desc = desc;
    }
    String getFieldsTab(int num)throws Exception{
        ArNode node = nodes.get(num2n(num));
        String s =
                "<tr><td>position</td><td><input type=text size=1 name=pos value='"+node.pos+"'></td></tr>"+
                "<tr><td>colname</td><td><input  name=col value='"+(node.col==null?"":node.col)+"'></td></tr>"+
                "<tr><td>order</td><td><input type=text size=1 name=ord value='"+node.ord+"'></td></tr>"+
                "";
        return s;
    }
    String getTitle(ArNode node){
        return "pos="+node.pos+";ord="+node.ord+";col="+node.col;
    }
    
    boolean isEmpty(ArNode node){
        return node.pos<=0;
    }
    String path(int num)throws Exception{
        String s = "";
        ArNode x = nodes.get(num2n(num));
        do{
            s=x.longName()+"/"+s;
            x = nodes.get(num2n(x.parentNum));
        }while(x.parentNum>0);
        s=rootNode.longName()+"/"+s;
        s="//"+s;
        
        return s;
    }
    String select(){
        List<ArNode> selectA = new ArrayList<ArNode>(),selectB;
        String select = "select ";
        
        for(ArNode node:nodes){
            if(node.isField()) selectA.add(node);
        }
        
        selectB = sortSelect(selectA);
        
        for(ArNode node:selectB){
            select+="\nt"+node.parentNum+node.tabname+"."+node.colname+" "+
                    (node.col!=null&& node.col.trim()!=""?"\"<b>"+node.col+"</b>\"":"")+
                    ",";
        }
//        ArNode t = selectB.get(0);
        startTableId=rootNode.tab()+".id";
        return select.substring(0,select.length()-1);
    }
    String order(){
        List<ArNode> orderA = new ArrayList<ArNode>(),orderB;
        int iorder=0;
        String order = " order by ";
        
        for(ArNode node:nodes){
            if(node.isField()&& node.ord>0){
                orderA.add(node);
            }
        }
        orderB = sortOrder(orderA);
        for(ArNode node:orderB){
            orderA.add(node);
            order+="t"+node.parentNum+node.tabname+"."+node.colname+",";
            iorder++;
        }
        
        order=order.substring(0,order.length()-1);
        return "\n where "+startTableId+">0 "+(iorder>0?order:"");
    }
    String from()throws Exception{
        String from = "\n from ";
        String leftCol="",rightCol="";
        from+=rootNode.tab2();
        boolean start=true;
        for(int i=0;i<nodes.size();i++){//
            ArNode typeNode = nodes.get(i);
            if(!typeNode.isType())continue;
            for(int i2=i+1;i2<nodes.size();i2++){
                ArNode tableNode = nodes.get(i2);
                if(!tableNode.isTable())continue;
                if(tableNode.parentNum!=typeNode.num)continue;
                if(start){//the first level
                    start=false;
                    leftCol = typeNode.tab()+".id";
                    rightCol = tableNode.tab()+".id";
                    from+="\n join  "+tableNode.tab2()+" on "+leftCol+"="+rightCol;
                }else{//more levels
                    ArNode keyNode = getParent2(tableNode);
                    ArNode keyTableNode = getParent(keyNode);                    
                    leftCol = keyTableNode.tab()+"."+keyNode.colname;
                    rightCol = tableNode.tab()+".id";
                    from+="\n\n join  "+tableNode.tab2()+" on "+leftCol+"="+rightCol;
                }
            }
        }
        return from;
    }
    
    ArNode getParent(ArNode node)throws Exception{
        int parent = node.parentNum;
        return nodes.get(num2n(parent));
    }
    
    ArNode getParent3(ArNode node)throws Exception{
        return getParent(getParent(getParent(node)));
    }
    ArNode getParent2(ArNode node)throws Exception{
        return getParent(getParent(node));
    }
    String qtxt()throws Exception{
        return select() + from() + order();
    }
    void record(String qx)throws Exception{
        String q = "update query set body='"+qx+"' where id="+qid;
        pp.exec(q);
    }
    List<ArNode> sortSelect(List<ArNode> A){
        if(A==null || A.size()==0)return A;
        List<ArNode> B = new ArrayList<ArNode>();
        ArNode n = A.get(0);
        B.add(n);
        for(int i=1;i<A.size();i++){
            ArNode nn = A.get(i);
            ArNode n0 = B.get(0);
            if(nn.pos<n0.pos){
                B.add(0,nn);
            }else{
                boolean in=false;
                for(int j=0;j<B.size()-1;j++){
                    ArNode n1 = B.get(j);
                    ArNode n2 = B.get(j+1);
                    if(nn.pos>=n1.pos && nn.pos<n2.pos){
                        B.add(j+1,nn); in=true; break;
                    }
                }
                if(!in)B.add(nn);
            }
        }
        return B;
    }
    List<ArNode> sortOrder(List<ArNode> A){
        if(A==null || A.size()==0)return A;
        List<ArNode> B = new ArrayList<ArNode>();
        ArNode n = A.get(0);
        B.add(n);
        for(int i=1;i<A.size();i++){
            ArNode nn = A.get(i);
            ArNode n0 = B.get(0);
            if(nn.ord<n0.ord){
                B.add(0,nn);
            }else{
                boolean in=false;
                for(int j=0;j<B.size()-1;j++){
                    ArNode n1 = B.get(j);
                    ArNode n2 = B.get(j+1);
                    if(nn.ord>=n1.ord && nn.ord<n2.ord){
                        B.add(j+1,nn); in=true; break;
                    }
                }
                if(!in)B.add(nn);
            }
        }
        return B;
    }
    
    void delete(HttpServletRequest req)throws Exception{
        SortedMap<String,String> map = GuiTree.req2map(req);
        for(String k:map.keySet()){
            String v = map.get(k);
            if(!k.equals("x")&&!k.equals("y")&&v!=null){
                int num = Integer.parseInt(k);
                delete(num);
            }
        }
    }
    
    void delete(int num)throws Exception{
        int n = num2n(num);
        collapse(n);
        nodes.remove(n);
    }
    public int num2n(int Num)throws Exception{
        for(int i=0;i<nodes.size();i++){
            if(nodes.get(i).num==Num)
                return i;
        }
        throw new Exception("gui tree:num="+Num+"?");
    }
    public String show() throws Exception{
        String S="";
        String sL,sR;
        for(int i=0;i<nodes.size();i++){
            ArNode node = nodes.get(i);
            sL="<tr><td>";
            for(int k=0;k<node.depth;k++){
                sL+=iconImg("line")+"</td><td>";}
            if(!node.collapsed){
                sL+="<a title='click to hide next level:"+
                        node.parentNum+"' href=collapse.jsp?id="+
                        node.num+">"+iconImg("minus")+"</a></td><td>";
            } else{
                if(hasChildren(node)){
                    sL+=
                            "<a title='click to see next level:"+node.colname+
                            //node.ntype+
                            //node.parentNum+
                            "' href=expand.jsp?id="+
                            node.num+">"+iconImg("plus")+"</a></td><td>";
                } else{
                    sL+=iconImg("treelines")+"</td><td>";
                }
            }
            
            String a=node.isField()?"<a title='"+getTitle(node)+"'  href='object.jsp?id="+
                    node.num+"'>":"";
            String a2 = node.isField()?"</a>":"";
            sR=node.icon()+"</td><td colspan="+(20-node.depth)+
                    ">"+ 
                    (isEmpty(node)?"":"<b>")+
                    a +node.longName()+ a2 +
                    (isEmpty(node)?"":"</b>")+
                    "<input type='checkbox' name="+node.num+">"+
                    "</td></tr>";
            S+=sL+sR;
        }
        return S;
    }
    String iconImg(String img){
        return GuiTree.iconImg(img);
    }
    
    
}
