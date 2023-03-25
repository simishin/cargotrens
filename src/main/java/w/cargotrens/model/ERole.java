package w.cargotrens.model;

import org.springframework.security.core.context.SecurityContextHolder;

public enum ERole {
    GUEST("GUEST"),
    BOSS("ADMIN"),
    DISPATCHER("DISPC"),
    DRIVER("DRIVR");
    private String role;

    ERole(String role){ this.role=role; }
    public String getRole(){ return "ROLE_"+role;}
    public String get(){return role;}
    public boolean is(int i){return  Math.abs(i)== ordinal(); }
    public boolean is(){
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        if ( login.equals("anonymousUser") && this.ordinal()==0 ) return true;
//        assert prnv(">"+SecurityContextHolder.getContext().getAuthentication()
//                .getAuthorities()+"<--"+this.get()+"= "+SecurityContextHolder.getContext().getAuthentication()
//                .getAuthorities().toString().indexOf(this.get()));
        return SecurityContextHolder.getContext().getAuthentication()
                .getAuthorities().toString().indexOf(this.get())>0;
    }
}
