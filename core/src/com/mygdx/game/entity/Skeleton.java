package com.mygdx.game.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Skeleton extends Enemy{
    private Projectile projectile;
    private String State;
    private static Texture textureSkel = new Texture("Skeleton/SpriteSheet.png");
    private static Texture ProjText = new Texture("Skeleton/зуб.png");

    public Skeleton(float x, float y, float HP, float Damage, float AttackSpeed, float MoveSpeed, Vector2 PosProj) {
        super(x, y, HP, Damage, AttackSpeed, MoveSpeed);
            projectile = new Projectile(PosProj);
        SetAnim();
    }


    public void setState(String state) {
        State = state;
    }


    public void SetAnim(){
        texture = textureSkel;
        projectile.SetProjText(ProjText);
        EnemyAnim = new TextureRegion[texture.getWidth()][texture.getHeight()];
        CreateAnim();
    }

    @Override
    protected void CreateAnim() {
        super.CreateAnim();
    }

    @Override
    protected void Animation(Batch batch) {
        super.Animation(batch);
    }
    @Override
    protected void RandomMoving() {
        super.RandomMoving();
    }

    @Override
    public void Die() {
        super.Die();
    }

    @Override
    public void render(Batch batch) {
        projectile.RenderProj(batch);
        super.render(batch);
    }
    class Projectile{
        private Texture projectileTexture;
        private Vector2 PosProj;
        public Projectile(Vector2 PosProj){
            this.PosProj = PosProj;
        }
        public void SetProjText(Texture texture){
            projectileTexture = texture;
        }
        public Projectile(){}

        public void RenderProj(Batch batch){
            batch.draw(projectileTexture, PosProj.x,PosProj.y);
        }

    }

}
