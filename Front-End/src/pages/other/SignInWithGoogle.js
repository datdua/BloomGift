import { useEffect } from "react";
import { signInWithGoogle } from "../../redux/actions/authenticationActions";
import { useDispatch } from "react-redux";
import { useToasts } from "react-toast-notifications";
import { useHistory } from "react-router-dom";

function SignInWithGoogle() {
    const dispatch = useDispatch();
    const { addToast } = useToasts();
    const history = useHistory();
    
    useEffect(() => {
        dispatch(signInWithGoogle(addToast))
            .then((response) => {
                if (!response.ok) {
                    console.error("Google login failed:", response);
                } else {
                    console.log("Google login successful:", response);
                    localStorage.setItem("token", response.token);
                    history.push("/home-fashion");
                }
            })
            .catch((error) => {
                console.error("Google login error:", error);
                history.push("/not-found")
            })

    }, [dispatch, addToast, history]);

    return null; 
}

export default SignInWithGoogle;
