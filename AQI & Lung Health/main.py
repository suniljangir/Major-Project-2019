from flask import Flask, render_template, request, redirect, url_for, flash, session
import pandas as pd
import numpy as np
from flask_sqlalchemy import SQLAlchemy
from flask_admin import Admin
from flask_admin.contrib.sqla import ModelView
from flask_login import LoginManager, UserMixin, login_user, login_required, logout_user, current_user
from datetime import datetime,timedelta
import prediction
import aqi


app = Flask(__name__)

# flask admin
app.config['SQLALCHEMY_DATABASE_URI'] = 'sqlite://///home/nishant/Desktop/major project/admin.db'
app.config['SECRET_KEY'] = 'mysecret'

db = SQLAlchemy(app)
admin = Admin(app)
login_manager = LoginManager()
login_manager.init_app(app)


class User(UserMixin, db.Model):
    id = db.Column(db.Integer, primary_key=True, autoincrement=True)
    userid = db.Column(db.String(30), unique=True, nullable=False,)
    name = db.Column(db.String(30), nullable=False)
    dob = db.Column(db.DateTime, nullable=False)
    emailid = db.Column(db.String, nullable = False)
    password = db.Column(db.String(30), nullable=False)
    userinfo = db.relationship('UserInfo',backref='user',lazy=True)

    def __repr__(self):
        return "<User(userid='%s', name='%s', dob='%s', emailid='%s', password='%s')>" %(self.userid, self.name, self.dob, self.emailid, self.password)




class UserInfo(UserMixin, db.Model):
    id = db.Column(db.Integer, primary_key=True, autoincrement=True)
    name = db.Column(db.String(30),nullable=False)
    password = db.Column(db.String(30), nullable=False)
    emailid = db.Column(db.String(30), nullable=False)
    dob = db.Column(db.DateTime, nullable=False)
    result = db.Column(db.String(30),nullable=False)
    userid = db.Column(db.String(30), db.ForeignKey('user.userid'), nullable=False)

    def __repr__(self):
        return "<UserInfo(name='%s', password='%s', emailid='%s', dob='%s', result='%s')>" %(self.name, self.password, self.emailid, self.dob, self.result)

admin.add_view(ModelView(User, db.session))
admin.add_view(ModelView(UserInfo, db.session))

@login_manager.user_loader
def load_user(user_id):
    return User.query.get(int(user_id))

@app.before_request
def make_session_permanent():
    session.permanent = True
    app.permanent_session_lifetime = timedelta(minutes=5)


@app.route('/')
def home():
    return render_template("home.html")


@app.route('/info.html',methods=["GET","POST"])
def info():
    if request.method == "POST":
        userid = request.form['userid']
        password = request.form['password']

    print("################################################################")
    print(userid)
    print(password)
    print("################################################################")


    luser = UserInfo.query.filter_by(userid=userid).first()
    print(luser)
    print("################################################################")

    today_level_str = aqi.today_level_str

    if(luser==None):
        return "<html><head><script>alert('No user');</script></head></html>"

    else:

        login_user(luser)
        print(luser)
        print("############### user login done ##########################")
        session['userid'] = userid
        print(userid)
        print("############## user is in session #########################")
        name = luser.name
        originalPassword = luser.password
        emailid = luser.emailid
        result = luser.result

        print("#############################################################")
        print(name)
        print(originalPassword)
        print(emailid)
        print(result)
        print("#############################################################")

        if( originalPassword == password):
            return render_template("info.html",name=name,emailid=emailid,result=result,today_level_str=today_level_str)

        else:
            return "<html><head><script>alert('wrong password');</script></head></html>"

@app.route('/check.html', methods=['POST', 'GET'])
def check():

    if 'userid' in session:
        print("user already in session")
        userid = session['userid']
        print(userid)
        user = User.query.filter_by(userid=userid).first()
        name = user.name
        emailid = user.emailid
        return render_template("check.html", name=name, emailid=emailid, status="You are now logged in")

    else:
        if request.method == 'POST':
            name = request.form["name"]
            userid = request.form["userid"]
            dob = request.form["dob"]
            emailid = request.form["emailid"]
            password = request.form["password"]

        new = User(userid=userid, name=name, dob=datetime.strptime(dob,'%Y-%m-%d'), emailid=emailid, password=password)
        db.session.add(new)
        db.session.commit()

        user = User.query.filter_by(userid=userid).first()
        print("#####################################################################")
        print("#####################################################################")
        print("current user:"+str(user))
        login_user(user)
        session['userid'] = userid

        print("########################### USER LOGIN DONE ##########################################")
        print("#####################################################################")
        print("current session:"+str(session))
        if 'userid' in session:
            return render_template("check.html",name=name,emailid=emailid,status="You are now logged in")
        else:
            return "server error"



@app.route('/predict.html', methods=['POST', 'GET'])
@login_required
def predict():
    # getting data from home
    if request.method == 'POST':
        # age = int(request.form["age"])
        alcohol_use = int(request.form["alcoholuse"])
        occupational_hazard = int(request.form["occupationalhazard"])
        chronic_disease = int(request.form["chronicdisease"])
        obesity = int(request.form["obesity"])
        passive_smoking = int(request.form["passivesmoking"])
        chest_pain = int(request.form["chestpain"])
        coughing = int(request.form["coughing"])
        fatigue = int(request.form["fatigue"])
        shortness_breath = int(request.form["shortnessofbreath"])
        swallowing_difficulty = int(request.form["swallowingdifficulty"])
        dry_cough = int(request.form["drycough"])
        snoring = int(request.form["snoring"])
        wheezing = int(request.form["wheezing"])
    print(" #################################### ")
    print("data fetched from check form ")

    if 'userid' in session:
        userid = session['userid']


    print("#####################################################################")
    print(str(userid)+" is in session")
    print("#####################################################################")
    user = User.query.filter_by(userid=userid).first()
    name = user.name
    dob = user.dob
    emailid = user.emailid
    password = user.password
    print("#####################################################################")
    print("#####################################################################")
    print(user,name,dob,emailid,password)
    print("#####################################################################")
    print("#####################################################################")
    #print("after userid"+str(userid))
    #fetching airpollution
    air_pollution = aqi.air_pollution

    #calculating age
    now_year = datetime.now().year
    user_dob_year = user.dob.year
    print("#####################################################################")
    age = abs(now_year-user_dob_year)
    print(age)
    print("#####################################################################")

    l = np.array(
        [age, air_pollution, alcohol_use, occupational_hazard, chronic_disease, obesity,
         passive_smoking, chest_pain, coughing, fatigue, shortness_breath, wheezing, swallowing_difficulty,
         dry_cough, snoring], )
    l.resize(1, 15)
    user_input = pd.DataFrame(l)

    svc_user = prediction.svc_clf.predict(user_input)

    today_level = aqi.today_level
    tomorrow_level = aqi.tomorrow_level
    dayaftertomorrow_level = aqi.dayaftertomorrow_level

    today_level_str = aqi.today_level_str
    tomorrow_level_str = aqi.tomorrow_level_str
    dayaftertomorrow_level_str = aqi.dayaftertomorrow_level_str

    try:
        userinfo = UserInfo.query.filter_by(userid= userid).first()
        userinfo.result = svc_user[0]
        print("old user update")
        db.session.commit()

    except Exception as e:
        new = UserInfo(name=name, password=password, emailid=emailid, dob=dob, result=svc_user[0],userid=userid)
        db.session.add(new)
        db.session.commit()

    # new = UserInfo(name=name, password=password, emailid=emailid, dob=dob, result=svc_user[0], userid=userid)
    # db.session.add(new)
    # db.session.commit()

    return render_template("predict.html",name=name,emailid=emailid,svc_predict=svc_user[0],today_level=today_level,tomorrow_level=tomorrow_level,dayaftertomorrow_level=dayaftertomorrow_level,today_level_str=today_level_str,tomorrow_level_str=tomorrow_level_str,dayaftertomorrow_level_str=dayaftertomorrow_level_str)


@app.route('/logout')
#login_required
def logout():
    logout_user()
    session.pop('user', None)
    session.clear()
    print("############################# USER LOGOOUT DONE ########################################")
    return redirect(url_for('home'))


if __name__ == '__main__':
    app.run(host='0.0.0.0', debug=True, port=5000)
