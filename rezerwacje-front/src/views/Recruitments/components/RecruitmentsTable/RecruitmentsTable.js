import React from "react";
import {
  Card,
  CardHeader,
  CardContent,
  CardActions,
  Divider,
  Grid,
  Button,
  TextField,
  Typography,
  Radio,
  RadioGroup,
  FormControlLabel
} from "@material-ui/core";
import useForm from "./useForm";
import validateUserForm from "./validateRecruitmentForm";
import "./AddUserForm.scss";
import StyledRadio from "./StyledRadio"

const AddUserForm = () => {
  const initState = {
    firstName: "",
    lastName: "",
    email: "",
    phone: "",
    login: "",
    password: "",
    confirmPassword: "",
    role: ""
  };


  const { handleChange, handleSubmit, values, errors, setState } = useForm(
    initState,
    submit,
    validateUserForm
  );

  return (

  );
};

export default AddUserForm;
