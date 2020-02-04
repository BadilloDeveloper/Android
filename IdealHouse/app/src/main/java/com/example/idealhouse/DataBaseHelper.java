package com.example.idealhouse;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.sax.Element;

import java.util.ArrayList;
import java.util.List;


public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String FICHERO_ASOCIADO="BdAd.csv";

    public static final String DATABASE_NAME="BdAd.db";
    public static final String TABLA ="TableAdd";

    public static final String[] COLS ={"id","name","telephone","prize","meters","description","type","image","latitude","longitude"};
    public static final String[] TYPE ={"INTEGER PRIMARY KEY AUTOINCREMENT","TEXT","TEXT","LONG","LONG","TEXT","INTEGER","BLOB","DOUBLE","DOUBLE"};

    //  public static final int RESULTADOS_A_MOSTRAR=20;
    //crea base datos
    public static String CREATE_SQL(){
        String SQL="CREATE TABLE IF NOT EXISTS "+ TABLA +" (";
        for(int i = 0; i<(COLS.length-1); i++){
            SQL+= COLS[i]+" "+ TYPE[i]+", ";
        }
        SQL+= COLS[COLS.length-1]+" "+ TYPE[COLS.length-1]+") ";
        return SQL;
    }

    public static final String DROP_SQL="DROP TABLE IF EXISTS "+ TABLA +"";


    public DataBaseHelper(Context context){
        super(context, DATABASE_NAME, null, 1);
        getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_SQL());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_SQL);
        onCreate(db);
    }

    public void truncate(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(DROP_SQL);
        onCreate(db);
    }

    public List<ElementList> obtenerAnuncios(){

        List<ElementList> list=new ArrayList<ElementList>();

        SQLiteDatabase db=this.getReadableDatabase();
        String sqlc = "SELECT * FROM TableAdd";
        Cursor res = db.rawQuery(sqlc, null);
        res.moveToFirst();
        while(res.isAfterLast()==false){
            list.add(new ElementList(res));
            res.moveToNext();
        }
        res.close();
        return list;
    }
    public List<ElementList> obtenerAnuncios(int id,long min,long max){


        List<ElementList> list=new ArrayList<ElementList>();

        SQLiteDatabase db=this.getReadableDatabase();
        String sqlc = "SELECT * FROM TableAdd where  type = "+id + " AND  IFNULL(prize, 0) > "+min+"  AND IFNULL(prize, 0) <  "+max;
        //String[] vector=  new String[] {String.valueOf(min), String.valueOf(max)};
        Cursor res = db.rawQuery(sqlc, null);
        res.moveToFirst();
        while(!res.isAfterLast()){
            list.add(new ElementList(res));
            res.moveToNext();
        }
        res.close();
        return list;
    }

/*
        public List<Cliente> obtenerClientes(int pag){

            pag=(pag-1)*RESULTADOS_A_MOSTRAR;

            List<Cliente> list=new ArrayList<Cliente>();

            SQLiteDatabase db=this.getReadableDatabase();
            Cursor res = db.rawQuery("SELECT * FROM "+ NADADORES_TABLA +" LIMIT ?, "+RESULTADOS_A_MOSTRAR,new String[]{Integer.toString(pag)});
            res.moveToFirst();
            while(res.isAfterLast()==false){
                list.add(new Cliente(res));
                res.moveToNext();
            }
            res.close();
            return list;
        }

        //AUTOCOMPLETAR
        public Cursor obtenerClientesAutocompletar(String query, String columna1, String columna2){
            query=query.toLowerCase();
            SQLiteDatabase db=this.getReadableDatabase();
            String SQL="SELECT "+ NADADORES_COLS[0]+" AS _id, "+columna1+","+columna2+" ,"+ NADADORES_COLS[10]+" FROM "+
                    NADADORES_TABLA +" WHERE LOWER("+columna1+") LIKE '%"+query+"%' order by id DESC LIMIT 40";

            Cursor res=db.rawQuery(SQL,null);
            return res;
        }


    public Nadador obtenerNadadorId(long id){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + NADADORES_TABLA + " WHERE " + NADADORES_COLS[0] + "=" + id, null);
        res.moveToFirst();
        Nadador c=new Nadador(res);
        res.close();
        return c;
    }

        public List<Cliente> buscarClientes(String columna, String valor){

            List<Cliente> list=new ArrayList<Cliente>();

            SQLiteDatabase db=this.getReadableDatabase();
            Cursor res = db.rawQuery("SELECT * FROM "+ NADADORES_TABLA +"WHERE ?='?'",new String[] { columna,valor });
            res.moveToFirst();
            while(res.isAfterLast()==false){
                list.add(new Cliente(res));
                res.moveToNext();
            }
            res.close();
            return list;

        }

*/
/*
        public long insertarAnuncio(String csv){
            String aux[]=csv.split(";");
            SQLiteDatabase db = this.getWritableDatabase();

            //Si el csv tiene alguna fila erronea
            if(aux.length!=(COLS.length-1)){
                return -1;
            }

            ContentValues cv=new ContentValues();
            for(int i=0;i<aux.length;i++) {
                cv.put(COLS[i+1], aux[i]);
            }
            long id=db.insert(TABLA, null, cv);
            return id;
        }*/


    public int totalAdd(){
        SQLiteDatabase db=this.getReadableDatabase();
        return (int) DatabaseUtils.queryNumEntries(db, TABLA);
    }


    public ElementList insertarAnuncio(String name, String telefono, long price,long meters, String desc, int type,byte[]image,double latitude,double longitude){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(COLS[1],name);
        cv.put(COLS[2],telefono);
        cv.put(COLS[3],price);
        cv.put(COLS[4],meters);
        cv.put(COLS[5],desc);
        cv.put(COLS[6],type);
        cv.put(COLS[7],image);
        cv.put(COLS[8],latitude);
        cv.put(COLS[9],longitude);

        int id= (int) db.insert(TABLA, null, cv);
        return new ElementList(id,name,telefono,price,meters,desc,type,image,latitude,longitude);
    }

    public ElementList insertarAnuncio(ElementList elemento){
        return insertarAnuncio(elemento.getName(),elemento.getTelephone(),elemento.getPrice(),
                elemento.getSize(),elemento.getDescription(),elemento.getTipoAnuncio(),elemento.getImage(),elemento.getLatitude(),elemento.getLongitude());
    }
/*
    public Nadador actualizarNadador(String nombre, String apellidos, String nacimiento, String sexo, int imagen,int id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(NADADORES_COLS[1],nombre);
        cv.put(NADADORES_COLS[2],apellidos);
        cv.put(NADADORES_COLS[3],nacimiento);
        cv.put(NADADORES_COLS[4],sexo);
        cv.put(NADADORES_COLS[5],imagen);
        db.update(NADADORES_TABLA,cv,"id = ?",new String[]{Integer.toString(id)});
        return new Nadador(id,nombre,apellidos,nacimiento,sexo,imagen);
    }



    public Nadador actualizarNadador(Nadador nadador){
        return actualizarNadador(nadador.getNombre(),nadador.getApellidos(),nadador.getNacimiento(),
                nadador.getSexo(),nadador.getImagen(),nadador.getId());
    }

    public int borrarNadador(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(NADADORES_TABLA,"id = ? ",new String[] { Integer.toString(id) });
    }

    public int borrarNadador(Nadador nadador) {
        return borrarNadador(nadador.getId());
    }


}
*/
}

