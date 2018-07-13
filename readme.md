## Dependency Injecte(依赖注入)

###### 首先写个不使用依赖注入的示例

+ interface

```java
// House.java
public interface House {
    void prepareForWar();

    void reportForWar();
}
```

+ 新建两个实现 House 接口的类

```java
// Starks.java
public class Starks implements House {

    @Override
    public void prepareForWar() {
        //do something
        System.out.println(this.getClass().getSimpleName()+" prepared for war");
    }

    @Override
    public void reportForWar() {
        //do something
        System.out.println(this.getClass().getSimpleName()+" reporting..");
    }
}
```

```java
// Boltons.java
public class Boltons implements House {
    @Override
    public void prepareForWar() {
        //do something
        System.out.println(this.getClass().getSimpleName()+" prepared for war");
    }

    @Override
    public void reportForWar() {
        //do something
        System.out.println(this.getClass().getSimpleName()+" reporting..");
    }
}
```

+ 接着需要一个依赖于这两个的类的类

```java
public class War {

    private Starks starks;

    private Boltons boltons;

    public War(){
        starks = new Starks();
        boltons = new Boltons();

        starks.prepareForWar();
        starks.reportForWar();
        boltons.prepareForWar();
        starks.reportForWar();
    }

}
```

+ 下次改用依赖注入的方式实现这个类

```java
public class War {

    private Starks starks;
    private Boltons boltons;
    
    //DI - getting dependencies from else where via constructor
    public War(Starks starks, Boltons bolton){
        this.starks = starks;
        this.boltons = bolton;
    }

    public void prepare(){
        starks.prepareForWar();
        boltons.prepareForWar();
    }

    public void report(){
        starks.reportForWar();
        boltons.reportForWar();
    }

}
```

+ 从外部注入依赖的对象

```java
public class BattleOfBastards {

    public static void main(String[] args){

        Starks starks = new Starks();
        Boltons boltons = new Boltons();

        War war = new War(starks,boltons);
        war.prepare();
        war.report();
    }
}
```

###### 利用dagger2进行依赖注入

+ Dagger 2 works on Annotation processor. 需要了解一定的java注解知识

![](https://images2018.cnblogs.com/blog/596306/201807/596306-20180712162600096-601192821.png)

+ 首先了解dagger2最常用的2个注解 ```@Inject``` 和 ```@Component```

#### @Inject Annotation
可以作用于

+ 构造器
+ 字段
+ 方法

```@Inject```注解告诉dagger哪些方法，构造器或者字段是需要依赖注入

But ```@Inject``` doesn’t work everywhere:

+ Interfaces can’t be constructed.
+ Third-party classes can’t be annotated.
+ Configurable objects must be configured!

#### @Component Annotation
作用于接口

```@Component```dagger会生成一个实现该接口的class，该class会实现其中的方法，提供需要依赖的对象,相当于是一个代理

#### 修改的代码

添加默认构造器，并添加注解@Inject

```java
public class Boltons implements House {

    // 添加默认构造器，并添加注解@Inject
   @Inject
   public Boltons(){
    }

    @Override
    public void prepareForWar() {
        //do something
        System.out.println(this.getClass().getSimpleName()+" prepared for war");
    }

    @Override
    public void reportForWar() {
        //do something
        System.out.println(this.getClass().getSimpleName()+" reporting..");
    }
}
```

```java
public class Starks implements House {

    @Inject //Dagger 2
    public Starks(){
    }

    @Override
    public void prepareForWar() {
        //do something
        System.out.println(this.getClass().getSimpleName()+" prepared for war");
    }

    @Override
    public void reportForWar() {
        //do something
        System.out.println(this.getClass().getSimpleName()+" reporting..");
    }
}
```

```java
public class War {

    private Starks starks;

    private Boltons boltons;

    @Inject
    public War(Starks starks, Boltons bolton){
        this.starks = starks;
        this.boltons = bolton;
    }

    public void prepare(){
        starks.prepareForWar();
        boltons.prepareForWar();
    }

    public void report(){
        starks.reportForWar();
        boltons.reportForWar();
    }

}
```

+ 修改依赖加载方式

```java
public class BattleOfBastards {

    public static void main(String[] args){
//        Mannual DI
//        Starks starks = new Starks();
//        Boltons boltons = new Boltons();
//        War war = new War(starks,boltons);
//        war.prepare();
//        war.report();

//      Using Dagger 2
        BattleComponent component = DaggerBattleComponent.create();
        War war = component.getWar();
        war.prepare();
        war.report();

    }
}
```

###### 在build之后查看annotation  processor 自动生成的代码

+ 如图

![](https://images2018.cnblogs.com/blog/596306/201807/596306-20180712115858650-2051251254.png)

+ DaggerBattleComponent.java 为dagger自动生成的代码，已类名加上Dagger前缀命名, 该类实现了BattleComponent接口

```java
package com.explore.lin.didemo.javaDIdemo;

import javax.inject.Provider;

public final class DaggerBattleComponent implements BattleComponent {
  private Provider<War> warProvider;

  private DaggerBattleComponent(Builder builder) {
    assert builder != null;
    initialize(builder);
  }

  public static Builder builder() {
    return new Builder();
  }

  public static BattleComponent create() {
    return new Builder().build();
  }

  @SuppressWarnings("unchecked")
  private void initialize(final Builder builder) {

    this.warProvider = War_Factory.create(Starks_Factory.create(), Boltons_Factory.create());
  }

  @Override
  public War getWar() {
    return new War(new Starks(), new Boltons());
  }

  public static final class Builder {
    private Builder() {}

    public BattleComponent build() {
      return new DaggerBattleComponent(this);
    }
  }
}
```

+ 继续在component中添加方法

```java
@Component
interface BattleComponent {
    War getWar();
    //adding more methods
    Starks getStarks();
    Boltons getBoltons();
}
```

+ 观察dagger2生成的DaggerBattleComponent中

```java
  // DaggerBattleComponent.java 
  @Override
  public War getWar() {
    return new War(new Starks(), new Boltons());
  }

  @Override
  public Starks getStarks() {
    return new Starks();
  }
```

+ 如果我们删除```Boltons.java```构造器中的注解```@Inject```,会发现无法通过编译,因为在```war.java```的构造器中存在对```Boltons```的依赖

###### 总结

比如有个classA中存在对classB的依赖，用dagger2怎么实现呢

+ 一种

```java
class A {

    @Inject
    public A() {
    }

    @Inject
    B b;

    void act() {
        b.prepare();
    }
}

class B {
    @Inject
    public B() {

    }

    void prepare() {
        System.out.println("b.prepare()");
    }
}

@Component
interface AComponent {
    A a();
}

public class InjectDemo {

    public static void main(String[] args) {
        DaggerAComponent.create().a().act();
    }
}
```

+ 二，什么时候使用```@Provide```，比如你使用第三方库，或则B中构造器没有```@Inject```时

```java
class AP {
    @Inject
    public AP() {

    }
    @Inject
    BP bp;

    void act() {
        bp.prepare();
    }
}

class BP {
    void prepare() {
        System.out.println("bp.prepare()");
    }
}

@Component(modules = {APModule.class})
interface APComponent{
    AP ap();
}

@Module
class APModule {

    @Provides
    BP providerBP() {
        return new BP();
    }
}

public class ProvideDemo {
    public static void main(String[] main) {
        DaggerAPComponent.create().ap().act();
    }
}
```



###### 在android中应用dagger

+ 同样先新建不使用dagger的项目

参考[https://github.com/DaiHangLin/dependencyInjecte](https://github.com/DaiHangLin/dependencyInjecte)

+ 利用dagger2进行依赖注入
参考[https://github.com/DaiHangLin/dependencyInjecte/tree/dependencyinject](https://github.com/DaiHangLin/dependencyInjecte/tree/dependencyinject)

