public class  OrgHierarchy implements OrgHierarchyInterface {
    OrgHierarchy(){
        super();
    }
    int size=0;
class Node{
    Node(){
        super();
    }
    void resize(){
        Node temp[]= new Node[2*s];
        for(int i=0;i<s;i++){
            temp[i]=next[i];
        }
        Node next[] = new Node[2*s];
        for(int i=0;i<s;i++){
            next[i]=temp[i];
        }
    }  
    int level;
    int s=10;//s given big initial value(number of places in list) to prevent growing
    int daughters;//number of daughters actually present
    Node prev;
    int key;
    Node next[]= new Node[10]; //declares list of daughter nodes
}
class Node_Avl{
    //store keys to the employees
    Node key_Avl;
    Node_Avl parent;
    Node_Avl left;
    Node_Avl right;
    int id;
    int max(int a, int b){
        if(a>b)return a;
        else return b;
    }
    int height()throws UnbalancedException{    //returns height of avl tree in h time
        if(this.left==null&&this.right==null){//leaf
           return 1;
        }
        else if(this.left==null)return this.right.height();
        else if(this.right==null)return this.left.height();
        else if(this.left.height()-this.right.height()>1||this.left.height()-this.right.height()<-1){
            throw new UnbalancedException();  
        }
        else{
          return  max(this.left.height(),this.right.height());//subtrees within limit heights
        }
    }
    int add(Node_Avl first,Node_Avl n) {
        if(first.left==null&&n.id<first.id){
            first.left=n;
            n.parent=first;
            check(n);
            return 0;}
        else if(first.right==null&&n.id>first.id){
            first.right=n;
            n.parent=first;
            check(n);
            return 0;}
        else if(n.id<first.id){
            return add(first.left,n);
        }
        else if(n.id>first.id){
            return add(first.right,n);
        }
        else{
            add(root_Avl,n);
            return 0;//there to prevent syntax errors
        }
    }

    int remove(Node_Avl n){
        if(n.left==null&&n.right==null){
           if(n.parent.left!=null)
        if(n.parent.left.key_Avl==n.key_Avl)//left child
        {
            n.parent.left=null;
            n.parent=null;
            return 0;

        }
        if(n.parent.right!=null)
        if(n.parent.right.key_Avl==n.key_Avl)//right child
        {
            n.parent.right=null;
            n.parent=null;
            return 0;

        }
    } //takes care of leaf node deletion
     else{
         Node_Avl temp=new Node_Avl();
         if(temp.right!=null){
         temp=n.right;
         while(true){
            if(temp.left==null)break;
             temp=temp.left;
         }
        
         //temp has node just greater than n..we will replace n with temp 
             if(n.parent==null){//n is root
                temp.right.parent=temp.parent;//upwards link broken and formed
                temp.parent.left=temp.right;//dowwards link broken and formed
                temp.left=n.left;
                temp.right=n.right;
                temp.parent=null;
                return 0;

             }
             else if(n.parent.left.id==n.id){//if statement tells location of n in relation to its parent
                 temp.right.parent=temp.parent;
                 n.parent.left=temp;
                 temp.parent.left=temp.right;  //in case temp has any children they become the children of its parent
                 temp.parent=n.parent;
                 temp.left=n.left;
                 temp.right=n.right;
                 return 0;

             }
             else if(n.parent.right.id==n.id){
                temp.right.parent=temp.parent;
                n.parent.right=temp;
                temp.parent.left=temp.right;
                temp.parent=n.parent;
                temp.left=n.left;
                 temp.right=n.right;
                 return 0;
             }
     }
     else if(temp.left!=null){
        temp=n.left;
        while(true){
           if(temp.right==null)break;
            temp=temp.right;
        }
            if(n.parent==null){
               temp.left.parent=temp.parent;
               temp.parent.right=temp.left;
               temp.left=n.left;
               temp.right=n.right;
               temp.parent=null;
               return 0;

            }
            else if(n.parent.left.id==n.id){//if statement tells location of n in relation to its parent
                temp.left.parent=temp.parent;
                n.parent.left=temp;
                temp.parent.right=temp.left;  //in case temp has any children they become the children of its parent
                temp.parent=n.parent;
                temp.left=n.left;
                temp.right=n.right;
                return 0;

            }
            else if(n.parent.right.id==n.id){
               temp.left.parent=temp.parent;
               n.parent.left=temp;
               temp.parent.right=temp.left;
               temp.parent=n.parent;
               temp.left=n.left;
                temp.right=n.right;
                return 0;
            }

     }
    }
        return 0;
    }
    void single_r_left(Node_Avl n){
        //n=z n.left=y  n.left.left=x
        Node_Avl temp=new Node_Avl();
        temp=n.left.right;
        n.left.right=n;
        n.parent=n.left;
        n.left=temp;
    }
    void single_r_right(Node_Avl n){
         //n=z n.left=y  n.left.left=x
         Node_Avl temp=new Node_Avl();
         temp=n.right.left;
         n.right.left=n;
         n.parent=n.right;
         n.right=temp;
    }
    void double_r_left(Node_Avl n){
        single_r_right(n.left);
        single_r_left(n);
    }
    /*       O
               \
                 O
               / 
             O    
             left then right
    */
    void double_r_right(Node_Avl n){
        single_r_left(n.right);
        single_r_right(n);
    }
     /*       O
            /
           O 
             \
               O  
               right then left  
    */
    Node_Avl problem;
    void balance(Node_Avl n){
        //n is where impalance happens
        int res1=check(n);
        if(res1<0){//left side heavier
            int res2=check(n.left);
            if(res2<0){
                single_r_left(n.parent);
            }
            if(res2>0){
                double_r_right(n.parent);
            }
        }
        else if(res1>0){//right side heavier
            int res2=check(n.right);
            if(res2>0){
                single_r_right(n.parent);
            }
            if(res2<0){
                double_r_left(n.parent);
            }
           // System.out.println("("+n.key_Avl.key+")");
        }
    }

    int check(Node_Avl a){   //checks if tree is balanced...return unbalanced node in problem
        int res;
        try{
         res=a.height();
        }
        catch (UnbalancedException e){
        balance(a);
         res=a.height();
        }
        return res;
    }

}


Node_Avl root_Avl= new Node_Avl();


Node root = new Node();


private Node search(Node_Avl n,int id,int a){
    Node res=new Node();
    res.level=-1;//level =-1 not supported
    if(n.id==id&&a!=-1){ //found and not to be deleted
        res=n.key_Avl;
        return res;}
    else if(n.id==id&&a==-1) { //found and to be deleted
        res=n.key_Avl;
        root_Avl.remove(n);
        return res;}
    else if(id<n.id&&n.left!=null){
        return search(n.left,id,a);
    }
    else if(id>n.id&&n.right!=null){
        return search(n.right,id,a);
    }
    else return res;
}
//O(logn)

public boolean isEmpty(){
    return (size==0);
}
//O(1)

public int size(){
    return size;
}
//O(1)

public int level(int id) throws IllegalIDException{
    Node n = search(root_Avl,id,0);
    if(n.level==-1) throw new IllegalIDException("ID Not Found");
    else return n.level; 

} //O(logn)

public void hireOwner(int id) throws NotEmptyException{
    if(size!=0) throw new NotEmptyException("There's already an owner");
    root.key=id;
    root_Avl.key_Avl=root;
    root_Avl.id=id;
    root.prev=null;//parent is null
    root.level=1;//root has level 0
    root_Avl.parent=null;//parent is null
    root_Avl.left=null;
    root_Avl.right=null;//no daughters for now
    root.daughters=0;
    size=1;
   // System.out.println("("+root.level+")");
}
//O(1)

public void hireEmployee(int id, int bossid) throws IllegalIDException{
   Node n = search(root_Avl,bossid,0);
   if(n.level==-1)throw new IllegalIDException("ID not found");
   if(n.daughters==n.s) n.resize();
   Node x= new Node();
   Node_Avl a = new Node_Avl();
   a.key_Avl=x;
   a.id=id;
   root_Avl.add(root_Avl,a);
   x.prev=n;
   x.key=id;
   size+=1;
   n.next[n.daughters]=x;
   n.daughters+=1;   
   x.level=(n.level+1);//the level of x is one greater than its parent
   //System.out.println("("+n.level+")");
}
//O(logn)(including balancing and adding)
public void fireEmployee(int id) throws IllegalIDException{
    Node n = search(root_Avl,id,-1);//remove from avl with -1(avl node is gone)
    int pos=-1;
   if(n.level==-1||n.level==1)throw new IllegalIDException("ID not found");//level 1 cannot be fired...why get rid of owner

    for(int i=0;i<n.prev.daughters;i++){
        if(n.prev.next[i].key==id){
            pos=i;
            break;
        }
    }
    Node temp;
        for(int i=pos+1;i<n.prev.daughters;i++){
            temp=n.prev.next[i-1];
            n.prev.next[i-1]= n.prev.next[i];
            n.prev.next[i]=temp;//puts value to be fired at last place
        }
    
       // n.prev.next[n.daughters]=null;
         n.prev.daughters=n.prev.daughters-1;
         n.prev=null;
    size-=1;
   // all references to this node are gone and we now wait for the garbage collector to manage everything else
}
//O(logn)

public void fireEmployee(int id, int manageid) throws IllegalIDException{
    Node n1 = search(root_Avl,id,-1);
    Node n2 = search(root_Avl,manageid,0);
    if(n1.level==-1||n2.level==-1)throw new IllegalIDException("One or both of the two IDs is invalid");
    else {
        int pos=-1;
        for(int i=0;i<n1.prev.daughters;i++){
            if(n1.prev.next[i].key==id){
                pos=i;//pos now has position of the
                break;
            }
        }
        for(int i=0;i<n1.daughters;i++){
            if(n2.s==n2.daughters)n2.resize();
            n2.next[n2.daughters]=n1.next[i];
            n1.next[i].prev=n2;
            n2.daughters+=1;
        }  
        Node temp;
        for(int i=pos+1;i<n1.prev.daughters;i++){//.prev is used because it is assumed that Owner cannot be fired
            temp=n1.prev.next[i-1];
            n1.prev.next[i-1]= n1.prev.next[i];
            n1.prev.next[i]=temp;//puts value to be fired at last place
        }
        n1.prev.daughters-=1;
        n1.prev=null;
    size-=1;
}
}
//O(logn)

public int boss(int id) throws IllegalIDException{
Node n=search(root_Avl,id,0);
if(n.level==-1)throw new IllegalIDException("ID not found");
if(n.prev==null)return -1;
else return n.prev.key;
}
//O(logn)
private Node lowestCommon(Node n1, Node n2) {
    //  Node res = new Node();
        if(n1.key==n2.key)return n1;
        if(n1.level<n2.level){
           return lowestCommon(n1, n2.prev);
         }
         else{
           return lowestCommon(n1.prev, n2);
         }
}
public int lowestCommonBoss(int id1, int id2) throws IllegalIDException{
    Node n1 = search(root_Avl,id1,0);
    Node n2 = search(root_Avl,id2,0);
    if(n1.level==-1||n2.level==-1||n1.level==1||n2.level==1) throw new IllegalIDException("One or both of the two IDs is invalid");
    if(n1.prev.key==n2.key) return n2.prev.key;//lowest common boss in this case is boss of n2
    if(n2.prev.key==n1.key) return n1.prev.key;
    Node res=new Node();
        if(n1.level<=n2.level){
           res = lowestCommon(n1, n2.prev);//replace with another function that takes nodes as args to reduce complexity of search every time
        }
        else{
           res = lowestCommon(n1.prev, n2);
        }
return res.key;
}

String T;
int num=10;
int que[][]=new int[num+5][2];
int info[][]=new int[num*100][2];
int index;
int front=0;
int rear=0;
/*int[][] int_resize(int a){
    int temp[][]=new int[num][2];
    if(a==0){
    for(int i=0;i<num;i++){
        temp[i][0]=que[i][0];
        temp[i][1]=que[i][1];
    }
    int que[][]=new int[num*2][2];
    num=num*2;
    for(int i=0;i<num/2;i++){
        que[i][0]=temp[i][0];
        que[i][1]=temp[i][1];
    } 
    return que;}
    else{
    int t[][]=new int[num*100][2];
    if(a==0){
    for(int i=0;i<num*100;i++){
        t[i][0]=info[i][0];
        t[i][1]=info[i][1];
    }
    int info[][]=new int[num*100*2][2];
    num=num*2;
    for(int i=0;i<num*100/2;i++){
        info[i][0]=t[i][0];
        info[i][1]=t[i][1];
    } 
    return info;
    }
    return que;
}}*///it is assumed that the size taken is already large enough so this wont be needded...if organization indeded has more than 1000 employees then its garbage in agrbage out


int lv;
private int prints(Node t){
   for(int i=0;i<t.daughters;i++){
    que[rear%num][0]=t.next[i].key;
    que[rear%num][1]=t.level;
    rear++;
 //   if((rear+1)%num==front%num)int_resize(1);
}
  //  if(index==num)int_resize(0);
    info[index][0]=que[front%num][0];
    info[index][0]=que[front%num][0];
    info[index][1]=que[front%num][1];
    info[index][1]=que[front%num][1];
    index++;
    front++;
    for(int i=0;i<t.daughters;i++){
    prints(t.next[i]);
    }
    return 0;
}

void mergesort(int beg,int end,int ind){
    int mid=beg+(end-beg)/2;
   /* for(int i=beg;i<end;i++){
        System.out.print("("+info[i][0]+","+info[i][1]+"<"+i+">"+")");
    }*/
    if(beg<end){
    //    System.out.println("Mergesort length 1 array");
    mergesort(beg,mid,ind);
    mergesort(mid+1,end,ind);
    merge(beg,mid,end,ind);
    }
}
void merge(int beg,int mid,int end,int ind){
//beg->mid first array mid+1->end second array()all indexes
int res[][]=new int[num*100][2];
int i1=beg;
int i2=mid+1;
int in=0;
for(int i=0;i1<=mid&&i2<=end;i++){  
    if(info[i1][ind]<info[i2][ind]){
        res[i][1]=info[i1][1];
        res[i][0]=info[i1][0];
        i1++;
        in=i;
    }
    else if(info[i1][ind]>info[i2][ind]){
        res[i][1]=info[i2][1];
        res[i][0]=info[i2][0];
        i2++;
        in=i;
    }
    else {
        res[i][1]=info[i1][1];
        res[i][0]=info[i1][0];
        i1++;
    in=i;}//both can be equal in level sorting cases
}
in++;
while(i1<=mid){
    res[in][1]=info[i1][1];
    res[in][0]=info[i1][0];
    i1++;
    in++;
}
while(i2<=end){
    res[in][1]=info[i2][1];
    res[in][0]=info[i2][0];
    i2++;
    in++;

}
//System.out.println("Mergeing");
for(int i=beg;i<=end;i++){
    info[i][0]=res[i-beg][0];
    info[i][1]=res[i-beg][1];
    in--;
}
}

/*void insertionsort(int beg,int end, int ind){
    int temp[]=new int [2];
    if(ind==3){
       // end++;
        //for(int i=beg-1;i<end;i++){
           // System.out.print("("+info[i][0]+","+info[i][1]+"<"+beg+","+end+">)");
        //}
        ind=ind-3;
       // beg=beg-1;
        //change stuff here for last case
    }
    if(beg!=0)
    beg=beg-1;
    for(int i=beg;i<end;i++){
        for(int j=i;j<end;j++){
            if(info[j][ind]<info[i][ind]){
                temp[0]=info[i][0];
                temp[1]=info[i][1];
                info[i][0]=info[j][0];
                info[i][1]=info[j][1];
                info[j][0]=temp[0];
                info[j][1]=temp[1];//swapping values
            }//lightest settles at bottom
        }
}
}*/
//insertion sort works...used simpler sorting algo to debug then reduced time complexity with mergesort

public String toString(int id) throws IllegalIDException{
    T=null;
    index=0;
    Node r = search(root_Avl,id,0);
    lv=r.level;
    if(r.level==-1)throw new IllegalIDException("ID not found");
    index=0;
    que[rear][0]=(r.key);
    que[rear][1]=r.level-1;
    info[index][0]=(r.key);
    info[index][1]=r.level;
    rear++;

    prints(r);
    mergesort(0,index-1,1);//sort based on level(1 for telling index of info to be sorted)
    int last=0;
   // System.out.println("Index:"+index);
    for(int i=0;i<index;i++){
        if(lv<info[i][1]){//reached the next level 
           mergesort(last, i-1, 0);//sorts elements on previous level
            last=i;
            lv++;
        }
    }
    mergesort(last, index-1, 0);//to sort the last level

    last=0;
    for(int i=0;i<index;i++){
        if(info[last][1]<info[i][1]){
            T=T+" "+info[i][0];//this had a comma before we were told that levels dont have to be seperated by commas
            last=i;
        }
        else T=T+" "+info[i][0];
    }
    front=rear=0;//for next to string
    num=10;
    lv=0;
    return T.substring(5);
}
}

class UnbalancedException extends RuntimeException{  
    UnbalancedException(){  
        super();  
    }  
}