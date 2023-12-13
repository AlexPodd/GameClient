package com.mygdx.game.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

public class Skeleton extends Enemy{
    private Projectile projectile;

    public Skeleton(float x, float y, float HP, float Damage, float AttackSpeed, float MoveSpeed) {
        super(x, y, HP, Damage, AttackSpeed, MoveSpeed);
        texture = new Texture("Skeleton/SpriteSheet.png");
        EnemyAnim = new TextureRegion[texture.getWidth()][texture.getHeight()];
        pos.set(x,y);
        CreateHitbox();
        CreateAnim();
        projectile = new Projectile();
    }
    public Circle GetProj(){
        return projectile.projectileCircle;
    }



    @Override
    protected void CreateAnim() {
        super.CreateAnim();
    }

    @Override
    protected void CreateHitbox() {
        super.CreateHitbox();
    }


    @Override
    protected void Animation(Batch batch) {
        super.Animation(batch);
    }

    @Override
    protected void Attack(Vector2 PlayerPos, Batch batch) {
            Vector2 StartPos = new Vector2(pos);
             projectile = new Projectile(StartPos, PlayerPos);
    }

    @Override
    protected void RandomMoving() {
        super.RandomMoving();
    }

    public void GetDamaged(float Damage){
        HP -=Damage;
        if(HP<0){

        }
    }

    @Override
    public void Die() {
        super.Die();
    }

    @Override
    public void render(Batch batch, Vector2 PlayerPos) {
        if(IsAttacking){
            projectile.UpdateProj();
            projectile.RenderProj(batch);
        }
        super.render(batch, PlayerPos);
    }
    class Projectile{
        private Texture projectileTexture;
        private Vector2 velocity;
        private Circle projectileCircle;
        private Vector2 PosProj;
        public Projectile(){

        }
        public Projectile(Vector2 StartPos, Vector2 EndPos){
            projectileTexture = new Texture("Skeleton/зуб.png");
            PosProj = StartPos;
            projectileCircle = new Circle(StartPos.x+8, StartPos.y+8, 1);
            velocity = EndPos.cpy().sub(pos).nor().scl(1.5F);
        }
        public void UpdateProj(){
            PosProj.add(velocity);
            projectileCircle.setPosition(PosProj);
        }
        public void RenderProj(Batch batch){
            batch.draw(projectileTexture, PosProj.x,PosProj.y);
        }

    }
}
